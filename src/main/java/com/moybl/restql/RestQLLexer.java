package com.moybl.restql;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RestQLLexer implements Lexer {

	private static final Map<String, Token> PUNCTUATORS = new HashMap<String, Token>() {{
		put("!", Token.NOT);
		put(":", Token.EQUAL);
		put("!:", Token.NOT_EQUAL);
		put("<", Token.LESS);
		put(">", Token.GREATER);
		put("<:", Token.LESS_OR_EQUAL);
		put(">:", Token.GREATER_OR_EQUAL);
		put(".", Token.OR);
		put(",", Token.AND);
		put("~", Token.LIKE);
		put("(", Token.OPEN_PARENTHESIS);
		put(")", Token.CLOSE_PARENTHESIS);
	}};

	private BufferedInputStream stream;

	public RestQLLexer(InputStream stream) {
		this.stream = new BufferedInputStream(stream);
	}

	public Symbol next() {
		Symbol symbol = null;

		try {
			int ch;

			while ((ch = stream.read()) != -1) {
				if (Character.isDigit(ch)) {
					StringBuffer sb = new StringBuffer();

					do {
						sb.append((char) ch);
						stream.mark(1);
						ch = stream.read();
					} while (Character.isDigit(ch));

					stream.reset();

					symbol = new Symbol(Token.INTEGER, sb.toString());

					break;
				} else if (Character.isLetter(ch) || ch == '_') {
					StringBuffer sb = new StringBuffer();

					do {
						sb.append((char) ch);
						stream.mark(1);
						ch = stream.read();
					} while (Character.isLetterOrDigit(ch) || ch == '_');

					stream.reset();

					symbol = new Symbol(Token.IDENTIFIER, sb.toString());

					break;
				} else if (ch == '\'') {
					StringBuffer sb = new StringBuffer();
					sb.append((char) ch);

					do {
						ch = stream.read();

						sb.append((char) ch);

						if (ch == '\'') {
							stream.mark(1);
							int next = stream.read();

							if (next == '\'') {
								sb.append('\'');
							} else {
								stream.reset();
								break;
							}
						} else if (ch == -1) {
							throw new RestQLException("string literal is not properly closed by a apostrophe");
						}
					} while (true);

					symbol = new Symbol(Token.STRING, sb.toString());

					break;
				} else if (!Character.isWhitespace(ch)) {
					String lexeme = Character.toString((char) ch);

					if (ch == '>' || ch == ':' || ch == '!' || ch == '<') {
						stream.mark(1);

						if (stream.read() == ':') {
							lexeme += ':';
						} else {
							stream.reset();
						}
					}

					if (!PUNCTUATORS.containsKey(lexeme)) {
						throw new RestQLException("illegal character");
					}

					symbol = new Symbol(PUNCTUATORS.get(lexeme), lexeme);

					break;
				}
			}
		} catch (Exception e) {
			throw new RestQLException(e.getMessage());
		}

		if (symbol == null) {
			symbol = new Symbol(Token.EOF, null);
		}

		return symbol;
	}

}