package com.pier.filetransfer.restcontroller;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pier.filetransfer.model.File;
import com.pier.filetransfer.model.HttpResponse;
import com.pier.filetransfer.model.UserFile;
import com.pier.filetransfer.service.FileService;
import com.pier.filetransfer.service.UserFileService;
import com.pier.filetransfer.service.UserService;
import com.pier.filetransfer.utils.Constants;
import com.pier.filetransfer.utils.JWTToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
@RestController
public class ApiRestController implements Constants {
	@Autowired
	UserService us;
	@Autowired
	FileService fs;
	@Autowired
	UserFileService ufs;

	@GetMapping(value = "/userFiles")
	public HttpResponse userFiles(HttpServletRequest request) {
		try {
			String jwt = request.getHeader("Authorization");
			Jws<Claims> claims = JWTToken.validate(jwt);
			String username = claims.getBody().getSubject();
			List<File> files = fs.findByUsername(username);
			return new HttpResponse(0, files);
		} catch (Exception exc) {
			System.err.println(exc.getMessage());
			return new HttpResponse(-1, "JWT tampered. Logout and log back in.");
		}
	}

	@PostMapping(value = "/upload")
	public HttpResponse upload(HttpServletRequest request, @RequestPart("file") MultipartFile file) {
		try {
			String jwt = request.getHeader("Authorization");
			Jws<Claims> claims = JWTToken.validate(jwt);
			String fileName = file.getOriginalFilename();
			String username = claims.getBody().getSubject();
			for (File f : fs.findByUsername(username)) {
				if (f.getName().equals(fileName))
					return new HttpResponse(1, "File " + fileName + " already exists.");
			}

			String path = BASE_PATH + username + "\\" + fileName;

			try (OutputStream os = new FileOutputStream(new java.io.File(path))) {
				os.write(file.getBytes());
			}

			File tmp = new File(file.getOriginalFilename(), path, new Date());
			File uf = fs.saveFile(tmp);

			ufs.saveUserFile(new UserFile(uf.getId(), username));

			return new HttpResponse(0, "File " + file.getOriginalFilename() + " uploaded successfully.");
		} catch (Exception exc) {
			exc.printStackTrace();
			return new HttpResponse(-1, exc.getMessage());
		}
	}

	@GetMapping(value = "/delete/{id}")
	public HttpResponse delete(@PathVariable("id") long id) {

		Optional<File> file = fs.findById(id);
		if (file.isEmpty())
			return new HttpResponse(1, "File does not exists.");

		UserFile uf = ufs.findById(id).get();
		fs.deleteFile(file.get());
		ufs.deleteById(id);
		new java.io.File(file.get().getUrl()).delete();
		List<File> files = fs.findByUsername(uf.getUsername());
		return new HttpResponse(0, files);
	}

	@GetMapping(value = "/download/{id}")
	public ResponseEntity<InputStreamResource> download(@PathVariable("id") long id) throws IOException {
	    Optional<File> fileOptional = fs.findById(id);
	    if (fileOptional.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    File file = fileOptional.get();
	    Path path = Paths.get(file.getUrl());
	    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("filename", file.getName());

	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(Files.size(path))
	            .body(new InputStreamResource(new ByteArrayInputStream(resource.getByteArray())));
	}


}
