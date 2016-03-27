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
		} while (accept(Token.AMPERSAND) && peek() != Token.EOF);

		return new Query(elements);
	}

	private AstNode parseAssignment() {
		AstNode or = parseOr();

		if (accept(Token.ASSIGNMENT)) {
			return new Assignment(or, parseSequence());
		}

		return or;
	}

	private AstNode parseSequence() {
		AstNode or = parseOr();

		if (accept(Token.COMMA)) {
			List<AstNode> elements = new ArrayList<AstNode>();

			do {
				elements.add(parseOr());
			} while (accept(Token.COMMA));

			return new Sequence(elements);
		}

		return or;
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
		AstNode member = parseMember();

		if (accept(Token.LESS, Token.LESS_OR_EQUAL, Token.GREATER, Token.GREATER_OR_EQUAL)) {
			return new BinaryOperation(current.getToken(), member, parseRelation());
		}

		return member;
	}

	private AstNode parseMember() {
		AstNode primary = parsePrimary();

		if (accept(Token.OPEN_PARENTHESIS)) {
			Call call = new Call(primary, parseSequence());
			check(Token.CLOSE_PARENTHESIS);

			return call;
		} else if (accept(Token.DOT)) {
			return new Member(primary, parseMember());
		}

		return primary;
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
			return new Literal(current.getLexeme(), Literal.Type.NUMBER);
		}

		if (accept(Token.STRING)) {
			return new Literal(current.getLexeme(), Literal.Type.STRING);
		}

		Report.error();

		return null;
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
			Report.errorf(Report.UNEXPECTED_TOKEN, token, next.getToken());
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
