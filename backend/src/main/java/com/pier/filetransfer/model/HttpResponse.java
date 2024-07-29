package com.pier.filetransfer.model;

import java.io.Serializable;

public class HttpResponse implements Serializable {
	private static final long serialVersionUID = 4364682746111153985L;
	
	private int code;
	private Object message;
	
	public HttpResponse(int code, Object message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
}
