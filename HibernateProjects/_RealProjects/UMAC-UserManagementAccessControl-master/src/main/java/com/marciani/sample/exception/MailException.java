package com.marciani.sample.exception;

public class MailException extends Exception {

	private static final long serialVersionUID = 7473733078335306700L;
	
	public MailException(String message) {
		super(message);
	}
	
	public MailException() {
		super();
	}

}
