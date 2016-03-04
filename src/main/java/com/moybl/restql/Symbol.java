package com.moybl.restql;

public class Symbol {

	private Token token;
	private String lexeme;

	public Symbol() {
	}

	public Symbol(Token token, String lexeme) {
		this.token = token;
		this.lexeme = lexeme;
	}

	public String getLexeme() {
		return lexeme;
	}

	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return String.format("Symbol(%s, '%s')", token, lexeme);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		Symbol other = (Symbol) obj;

		return token == other.token && lexeme.equals(other.lexeme);
	}

}
