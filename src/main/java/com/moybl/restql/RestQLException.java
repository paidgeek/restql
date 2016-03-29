package com.moybl.restql;

public class RestQLException extends RuntimeException {

	public RestQLException() {
		super("internal error");
	}

	public RestQLException(String message) {
		super(message);
	}

}
