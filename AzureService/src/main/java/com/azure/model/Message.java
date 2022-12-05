package com.azure.model;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = -8937172301407187498L;
	
	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
