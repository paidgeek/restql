package com.moybl.restql;

public class Report {

	public static final String INTERNAL_ERROR = "Internal error";
	public static final String UNEXPECTED_TOKEN = "Expected %s, got %s";
	public static final String ILLEGAL_CHARACTER = "Illegal character '%s'";
	public static final String STRING_LITERAL_NOT_CLOSED = "String literal is not properly closed by a apostrophe";

	public static void error() {
		throw new RestQLException(INTERNAL_ERROR);
	}

	public static void error(String message) {
		throw new RestQLException(message);
	}

	public static void errorf(String messageFormat, Object... args) {
		throw new RestQLException(String.format(messageFormat, args));
	}

}
