package com.moybl.restql;

import java.io.*;
import java.util.*;

public class RestQLLexer implements Lexer {

	private static final Map<String, Token> PUNCTUATORS = new HashMap<String, Token>() {{
		put("!", Token.NOT);
		put(":", Token.EQUAL);
		put("!:", Token.NOT_EQUAL);
		put("<", Token.LESS);
		put(">", Token.GREATER);
		put("<:", Token.LESS_OR_EQUAL);
		put(">:", Token.GREATER_OR_EQUAL);
		put("(", Token.OPEN_PARENTHESIS);
		put(")", Token.CLOSE_PARENTHESIS);
		put("=", Token.ASSIGNMENT);
		put("&", Token.AMPERSAND);
		put(",", Token.COMMA);
		put(".", Token.DOT);
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
					StringBuilder sb = new StringBuilder();

					do {
						sb.append((char) ch);
						stream.mark(1);
						ch = stream.read();
					} while (Character.isDigit(ch));

					stream.reset();

					symbol = new Symbol(Token.NUMBER, sb.toString());

					break;
				} else if (Character.isLetter(ch) || ch == '_') {
					StringBuilder sb = new StringBuilder();

					do {
						sb.append((char) ch);
						stream.mark(1);
						ch = stream.read();
					} while (Character.isLetterOrDigit(ch) || ch == '_');

					stream.reset();

					String lexeme = sb.toString();

					if (lexeme.equals("and")) {
						symbol = new Symbol(Token.AND, lexeme);
					} else if (lexeme.equals("or")) {
						symbol = new Symbol(Token.OR, lexeme);
					} else {
						symbol = new Symbol(Token.IDENTIFIER, lexeme);
					}

					break;
				} else if (ch == '\'') {
					StringBuilder sb = new StringBuilder();
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
							Report.error(Report.STRING_LITERAL_NOT_CLOSED);
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
						Report.errorf(Report.ILLEGAL_CHARACTER, lexeme);
					}

					symbol = new Symbol(PUNCTUATORS.get(lexeme), lexeme);

					break;
				}
			}
		} catch (IOException e) {
			Report.error();
		}

		if (symbol == null) {
			symbol = new Symbol(Token.EOF, null);
		}

		return symbol;
	}

}
