package com.moybl.restql;

import com.moybl.restql.ast.*;

import java.util.ArrayList;
import java.util.List;

public class RestQLParser implements Parser {

	private Lexer lexer;
	private Symbol current;
	private Symbol next;

	public Query parse(Lexer lexer) {
		this.lexer = lexer;
		next = lexer.next();

		List<AstNode> elements = new ArrayList<AstNode>();

		if (peek() == Token.EOF) {
			return new Query(elements);
		}

		do {
			elements.add(parseAssignment());
		} while (accept(Token.AMPERSAND));

		check(Token.EOF);

		return new Query(elements);
	}

	private AstNode parseAssignment() {
		AstNode sequence = parseSequence();

		if (accept(Token.ASSIGNMENT)) {
			return new Assignment(sequence, parseSequence());
		}

		return sequence;
	}

	private AstNode parseSequence() {
		List<AstNode> elements = new ArrayList<AstNode>();

		do {
			elements.add(parseOr());
		} while (accept(Token.COMMA));

		return new Sequence(elements);
	}

	private AstNode parseOr() {
		AstNode and = parseAnd();

		if (accept(Token.OR)) {
			return new BinaryOperation(Token.OR, and, parseOr());
		}

		return and;
	}

	private AstNode parseAnd() {
		AstNode equality = parseEquality();

		if (accept(Token.AND)) {
			return new BinaryOperation(Token.AND, equality, parseAnd());
		}

		return equality;
	}

	private AstNode parseEquality() {
		AstNode relation = parseRelation();

		if (accept(Token.EQUAL, Token.NOT_EQUAL)) {
			return new BinaryOperation(current.getToken(), relation, parseEquality());
		}

		return relation;
	}

	private AstNode parseRelation() {
		AstNode unary = parseUnary();

		if (accept(Token.LESS, Token.LESS_OR_EQUAL, Token.GREATER, Token.GREATER_OR_EQUAL)) {
			return new BinaryOperation(current.getToken(), unary, parseRelation());
		}

		return unary;
	}

	private AstNode parseUnary() {
		if (accept(Token.NOT)) {
			AstNode member = parseMember();

			return new UnaryOperation(Token.NOT, member);
		}

		return parseMember();
	}

	private AstNode parseMember() {
		AstNode primary = parsePrimary();

		if (accept(Token.OPEN_PARENTHESIS)) {
			Call call = new Call(primary, (Sequence) parseArguments());
			check(Token.CLOSE_PARENTHESIS);

			return call;
		} else if (accept(Token.DOT)) {
			return new Member(primary, parseMember());
		}

		return primary;
	}

	private AstNode parseArguments() {
		if (match(Token.CLOSE_PARENTHESIS)) {
			return new Sequence(new ArrayList<AstNode>());
		}

		return parseSequence();
	}

	private AstNode parsePrimary() {
		if (accept(Token.OPEN_PARENTHESIS)) {
			AstNode sequence = parseSequence();
			check(Token.CLOSE_PARENTHESIS);

			return sequence;
		}

		if (match(Token.IDENTIFIER)) {
			return parseIdentifier();
		}

		if (accept(Token.NUMBER)) {
			return new Literal(Double.parseDouble(current.getLexeme()), Token.NUMBER);
		}

		if (accept(Token.STRING)) {
			return new Literal(current.getLexeme()
											  .substring(1, current.getLexeme()
																		  .length() - 1), Token.STRING);
		}

		throw new RestQLException();
	}

	private Identifier parseIdentifier() {
		check(Token.IDENTIFIER);

		return new Identifier(current.getLexeme());
	}

	private Token peek() {
		return next.getToken();
	}

	private boolean match(Token... tokens) {
		for (Token token : tokens) {
			if (next.getToken() == token) {
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
			throw new RestQLException(RestQLException.UNEXPECTED_TOKEN, token, next.getLexeme());
		}
	}

	private boolean accept(Token... tokens) {
		for (Token token : tokens) {
			if (match(token)) {
				current = next;
				next = lexer.next();

				return true;
			}
		}

		return false;
	}

}
