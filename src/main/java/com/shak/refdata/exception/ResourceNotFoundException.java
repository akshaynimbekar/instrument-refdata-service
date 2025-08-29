package com.shak.refdata.exception;

public class ResourceNotFoundException extends RuntimeException{
	public ResourceNotFoundException(String messege) {
		super(messege);
	}
}
