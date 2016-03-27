package com.moybl.restql.factory;

import com.moybl.restql.Token;
import com.moybl.restql.ast.*;

import java.util.ArrayList;
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

	public RestQLBuilder call(AstNode target, AstNode arguments) {
		elements.add(new Call(target, arguments));

		return this;
	}

	public BinaryOperation binaryOperation(AstNode left, Token operator, AstNode right) {
		return new BinaryOperation(operator, left, right);
	}

	public Literal literal(Object value) {
		Literal.Type type;

		if (value instanceof Integer || value instanceof Float || value instanceof Double) {
			type = Literal.Type.NUMBER;
		} else {
			type = Literal.Type.STRING;
			value = value.toString();
		}

		return new Literal(value, type);
	}

	public Identifier identifier(String name) {
		return new Identifier(name);
	}

	public AstNode build() {
		return new Query(elements);
	}

}
