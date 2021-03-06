package com.moybl.restql.ast;

import com.moybl.restql.RestQLException;
import com.moybl.restql.Token;

public class Literal extends AstNode implements Comparable<Literal> {

	private Object value;
	private Token type;

	public Literal(Object value, Token type) {
		this.value = value;
		this.type = type;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public Object getValue() {
		return value;
	}

	public Token getType() {
		return type;
	}

	public double numberValue() {
		return (Double) value;
	}

	public String stringValue() {
		return (String) value;
	}

	@Override
	public String toString() {
		return String.format("Literal(%s, %s)", type, value);
	}

	public int compareTo(Literal o) {
		if (type != o.type) {
			throw new RestQLException("cannot compare a " + type + " with a " + o.type);
		}

		switch (type) {
			case NUMBER:
				return ((Double) value).compareTo((Double) o.value);
			case STRING:
				return ((String) value).compareTo((String) o.value);
		}

		return 0;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		Literal other = (Literal) obj;

		return value.equals(other.value);
	}

	public static Literal trueLiteral() {
		return new Literal(1.0, Token.NUMBER);
	}

	public static Literal falseLiteral() {
		return new Literal(0.0, Token.NUMBER);
	}

}
