package test.com.pier.filetransfer.utils;

import org.junit.jupiter.api.Test;

import com.pier.filetransfer.model.User;
import com.pier.filetransfer.utils.JWTToken;

class JWTTokenTest {

	@Test
	void generate() {
		User user = new User();
		user.setName("Piero");
		user.setSurname("Feltrin");
		user.setAge(23);
		user.setUsername("piero");
		
		String token = JWTToken.generate(user);
		System.out.println(token);
		String modifiedToken = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiUGllcm8iLCJzdXJuYW1lIjoiRmVsdHJpbiIsInN1YiI6InBpZXJvIiwianRpIjoiOTY0OGNmMDctZWFjYi00NjEwLWI5OTItMDA5ZjdlMzBlYWFhIiwiaWF0IjoxNzIxMDI4NTczLCJleHAiOjE3MjEwMjg4NzN9.ddjhqy4yiEFEE5FfNHYdpek8FRq9TMw0Eo1vm1nsNfa";
		
		JWTToken.validate(modifiedToken);
	}

}
