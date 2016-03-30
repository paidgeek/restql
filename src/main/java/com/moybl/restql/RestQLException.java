package com.moybl.restql;

public class RestQLException extends RuntimeException {

	public static final String INTERNAL_ERROR = "Internal error";
	public static final String UNEXPECTED_TOKEN = "Expected %s, got %s";
	public static final String ILLEGAL_CHARACTER = "Illegal character '%s'";
	public static final String STRING_LITERAL_NOT_CLOSED = "String literal is not properly closed by a apostrophe";

	public RestQLException() {
		super(INTERNAL_ERROR);
	}

	public RestQLException(String format, Object... args) {
		super(String.format(format, args));
	}

}
