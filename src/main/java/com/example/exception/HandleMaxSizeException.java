package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class HandleMaxSizeException extends Exception{

	private static final long serialVersionUID = 1L;
	public HandleMaxSizeException(String message){
    	super(message);
    }
}
