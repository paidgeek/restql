package com.moybl.restql;

import com.moybl.restql.ast.*;

import java.util.ArrayList;
import java.util.List;

public class RestQLParser implements Parser {

	private Lexer lexer;
	private Symbol current;
	private Symbol next;

	public AstNode parse(Lexer lexer) {
		this.lexer = lexer;
		next = lexer.next();

		return new Query(parseAssignments());
	}

	private List<Assignment> parseAssignments() {
		List<Assignment> assignments = new ArrayList<Assignment>();

		AstNode member = parseMember();
		check(Token.ASSIGNMENT);
		List<AstNode> elements = parseElements();

		assignments.add(new Assignment(member, elements));

		while (accept(Token.AMPERSAND)) {
			assignments.addAll(parseAssignments());
		}

		return assignments;
	}

	private List<AstNode> parseElements() {
		List<AstNode> elements = new ArrayList<AstNode>();

		elements.add(parseOr());

		while (accept(Token.COMMA)) {
			elements.addAll(parseElements());
		}

		return elements;
	}

	private AstNode parseElement() {
		return parseOr();
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
			Call call = new Call(primary, parseArguments());
			check(Token.CLOSE_PARENTHESIS);

			return call;
		} else if (accept(Token.DOT)) {
			return new Member(primary, parseMember());
		}

		return primary;
	}

	private AstNode parsePrimary() {
		if (accept(Token.OPEN_PARENTHESIS)) {
			AstNode element = parseElement();
			check(Token.CLOSE_PARENTHESIS);

			return element;
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

	private List<AstNode> parseArguments() {
		List<AstNode> arguments = new ArrayList<AstNode>();

		if (peek() != Token.CLOSE_PARENTHESIS) {
			arguments.add(parsePrimary());

			while (accept(Token.COMMA)) {
				arguments.addAll(parseArguments());
			}
		}

		return arguments;
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
