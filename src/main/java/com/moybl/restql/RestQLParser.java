package com.moybl.restql;

import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.BinaryOperation;
import com.moybl.restql.ast.Identifier;
import com.moybl.restql.ast.Literal;

public class RestQLParser implements Parser {

	private Lexer lexer;
	private Symbol current;
	private Symbol next;

	public AstNode parse(Lexer lexer) {
		this.lexer = lexer;
		next = lexer.next();

		return parseElement();
	}

	private AstNode parseElement() {
		return parseOr();
	}

	private AstNode parseOr() {
		AstNode child = parseAnd();

		if (accept(Token.OR)) {
			return new BinaryOperation(Token.OR, child, parseOr());
		}

		return child;
	}

	private AstNode parseAnd() {
		AstNode child = parseEquality();

		if (accept(Token.AND)) {
			return new BinaryOperation(Token.AND, child, parseAnd());
		}

		return child;
	}

	private AstNode parseEquality() {
		AstNode child = parseRelation();

		if (accept(Token.EQUAL, Token.NOT_EQUAL, Token.LIKE)) {
			return new BinaryOperation(current.getToken(), child, parseEquality());
		}

		return child;
	}

	private AstNode parseRelation() {
		AstNode child = parsePrimary();

		if (accept(Token.LESS, Token.LESS_OR_EQUAL, Token.GREATER, Token.GREATER_OR_EQUAL)) {
			return new BinaryOperation(current.getToken(), child, parseRelation());
		}

		return child;
	}

	private AstNode parsePrimary() {
		if (accept(Token.OPEN_PARENTHESIS)) {
			AstNode child = parseElement();

			check(Token.CLOSE_PARENTHESIS);

			return child;
		}

		if (accept(Token.IDENTIFIER)) {
			return new Identifier(current.getLexeme());
		}

		if (accept(Token.INTEGER)) {
			return new Literal(current.getLexeme(), Literal.Type.INTEGER);
		}

		if (accept(Token.DECIMAL)) {
			return new Literal(current.getLexeme(), Literal.Type.DECIMAL);
		}

		if (accept(Token.STRING)) {
			return new Literal(current.getLexeme(), Literal.Type.STRING);
		}

		throw new RestQLException("internal error");
	}

	private Token peek() {
		return next.getToken();
	}

	private boolean match(Token token) {
		if (next.getToken() == token) {
			return true;
		}

		return false;
	}

	private boolean match(Token... tokens) {
		for (int i = 0; i < tokens.length; i++) {
			if (next.getToken() == tokens[i]) {
				return true;
			}
		}

		return false;
	}

	private void check(Token token) {
		if (match(token)) {
			current = next;
			next = lexer.next();
		} else {
			throw new RestQLException(String.format("expected %s, got %s", token, next.getToken()));
		}
	}

	private boolean accept(Token token) {
		if (match(token)) {
			current = next;
			next = lexer.next();

			return true;
		}

		return false;
	}

	private boolean accept(Token... tokens) {
		for (int i = 0; i < tokens.length; i++) {
			if (accept(tokens[i])) {
				return true;
			}
		}

		return false;
	}

}
