package com.moybl.restql.factory;

import com.moybl.restql.Token;
import com.moybl.restql.ast.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestQLBuilder {

	private List<AstNode> elements;

	public RestQLBuilder() {
		elements = new ArrayList<AstNode>();
	}

	public RestQLBuilder assignment(AstNode destination, AstNode source) {
		elements.add(new Assignment(destination, source));

		return this;
	}

	public RestQLBuilder call(AstNode target, AstNode... arguments) {
		elements.add(new Call(target, new Sequence(Arrays.asList(arguments))));

		return this;
	}

	public BinaryOperation binaryOperation(AstNode left, Token operator, AstNode right) {
		return new BinaryOperation(operator, left, right);
	}

	public UnaryOperation unaryOperation(Token operator, AstNode child) {
		return new UnaryOperation(operator, child);
	}

	public Sequence sequence(AstNode... elements) {
		return new Sequence(Arrays.asList(elements));
	}

	public Literal literal(Object value) {
		Token type;

		if (value instanceof Integer || value instanceof Float || value instanceof Double) {
			type = Token.NUMBER;
		} else {
			type = Token.STRING;
			value = value.toString();
		}

		return new Literal(value, type);
	}

	public Member member(AstNode target, AstNode expression) {
		return new Member(target, expression);
	}

	public Identifier identifier(String name) {
		return new Identifier(name);
	}

	public AstNode build() {
		return new Query(elements);
	}

}
