package com.pier.filetransfer.restcontroller;

import java.io.File;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pier.filetransfer.model.HttpResponse;
import com.pier.filetransfer.model.User;
import com.pier.filetransfer.service.UserService;
import com.pier.filetransfer.utils.BCryptEncoder;
import com.pier.filetransfer.utils.Constants;
import com.pier.filetransfer.utils.JWTToken;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

class Attempt {
	public int count;
	public Instant time; 
}

@RequestMapping(value = "/")
@CrossOrigin(origins = "*")
@RestController
public class AuthRestController implements Constants{
	@Autowired
	UserService us;
	
	// String is the username
	private Map<String, Attempt> failedLogin = new HashMap<String, Attempt>();
	private final int WAITING_TIME = 60 * 3;
	
	private boolean regexCheck(User user) {
		boolean res = false;
		Pattern name = Pattern.compile("^[a-zA-Z ,.'-]{2,30}$");
		Pattern username = Pattern.compile("^[a-zA-Z0-9._-]{5,15}$");
		Pattern password = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#&%^$?=])[a-zA-Z0-9@#&%^$?=]{8,32}$");
	    Matcher matcher = name.matcher(user.getName());
	    res = matcher.find();
	    if (!res) return false;
	    matcher = name.matcher(user.getSurname());
	    res = matcher.find();
	    if (!res) return false;
	    matcher = username.matcher(user.getUsername());
	    res = matcher.find();
	    if (!res) return false;
	    matcher = password.matcher(user.getPassword());
	    res = matcher.find();
	    if (!res) return false;
	    res = (user.getAge() > 0 && user.getAge() < 100);
	    return res;
	}

	@PostMapping(value = "/register")
	public HttpResponse register(@RequestBody User request, HttpServletRequest httpRequest) {
		Cookie cookies[] = httpRequest.getCookies();
		
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("token") && !c.getValue().equals(""))
					return new HttpResponse(1, "Logout before new registration.");
			}			
		}

		if (us.findById(request.getUsername()).isPresent())
			return new HttpResponse(1, "User already exists.");
		
		if (!regexCheck(request))
			return new HttpResponse(1, "Some of the fields are not correct.");
		request.setPassword(BCryptEncoder.encode(request.getPassword()));
		us.saveUser(request);
		new File(BASE_PATH + request.getUsername()).mkdirs();
		return new HttpResponse(0, "User registered successfully.");
	}

	@PostMapping(value = "/login")
	public HttpResponse login(@RequestBody User userRequest, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		Attempt a = failedLogin.get(userRequest.getUsername());
		if (a != null && a.count > 4) {
			if (Instant.now().isBefore(a.time.plusSeconds(WAITING_TIME))) {
				long seconds = WAITING_TIME - Instant.now().minusSeconds(a.time.getEpochSecond()).getEpochSecond();
				return new HttpResponse(1, "Account has been blocked. Wait " +  seconds + " seconds before trying again.");
			} else {
				failedLogin.remove(userRequest.getUsername());
			}
		}
		
		Optional<User> user = us.findById(userRequest.getUsername());
		if (user.isEmpty()) {
			return new HttpResponse(1, "User does not exists.");
		}

		User u = user.get();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(userRequest.getPassword(), u.getPassword())) {
			Attempt attempt = failedLogin.get(userRequest.getUsername());
			if (attempt == null) {
				attempt = new Attempt();
			} else if (attempt.count == 4){
				attempt.time = Instant.now();
			} 
			attempt.count += 1;
			failedLogin.put(userRequest.getUsername(), attempt);
			return new HttpResponse(1, "Username or password incorrect.");
		}

		String token = JWTToken.generate(u);

		return new HttpResponse(0, token);
	}

	@GetMapping(value = "/logout")
	public HttpResponse logout(HttpSession session) {
		session.invalidate();
		return new HttpResponse(0, "Logout successfull.");
	}
}
