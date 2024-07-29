package com.pier.filetransfer.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptEncoder {
	
	public static String encode(String s) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(s);
	}
}
