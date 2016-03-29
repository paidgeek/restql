package com.moybl.restql;

import com.moybl.restql.ast.*;

import java.util.HashMap;
import java.util.Map;

public class Engine implements Visitor {

	private Map<String, Literal> variables;
	private Object result;

	public Engine() {
		variables = new HashMap<String, Literal>();
	}

	public void setVariable(String name, Object value) {
		if (value instanceof String) {
			variables.put(name, new Literal(value, Token.STRING));
		} else {
			variables.put(name, new Literal(value, Token.NUMBER));
		}
	}

	public Literal getVariable(String name) {
		return variables.get(name);
	}

	public void execute(String source) {
		RestQL.parse(source)
				.accept(this);
	}

	public void visit(Query acceptor) {
		for (AstNode element : acceptor.getElements()) {
			element.accept(this);
		}
	}

	public void visit(Assignment acceptor) {
		acceptor.getDestination()
				  .accept(this);
		Object var = result;
		acceptor.getSource()
				  .accept(this);

		if (var instanceof Identifier) {
			String dest = ((Identifier) var).getName();

			if (result instanceof Identifier) {
				variables.put(dest, variables.get(((Identifier) result).getName()));
			} else {
				variables.put(dest, (Literal) result);
			}
		} else {
			throw new RestQLException();
		}
	}

	public void visit(Literal acceptor) {
		result = acceptor;
	}

	public void visit(Identifier acceptor) {
		result = acceptor;
	}

	public void visit(UnaryOperation acceptor) {
		switch (acceptor.getOperator()) {
			case NOT:
				acceptor.getChild()
						  .accept(this);

				if (result instanceof Literal) {
					Literal literal = (Literal) result;

					if (literal.getType() == Token.STRING) {
						throw new RestQLException("operator '!' cannot be applied to string");
					} else {
						result = literal.numberValue() == 0.0 ? Literal.trueLiteral() : Literal.falseLiteral();
					}
				} else {
					throw new RestQLException();
				}

				break;
		}
	}

	public void visit(BinaryOperation acceptor) {
		acceptor.getLeft()
				  .accept(this);
		Object leftResult = result;
		acceptor.getRight()
				  .accept(this);
		Object rightResult = result;

		Literal left = null, right = null;

		if (leftResult instanceof Identifier) {
			left = variables.get(((Identifier) leftResult).getName());
		}

		if (rightResult instanceof Identifier) {
			right = variables.get(((Identifier) rightResult).getName());
		}

		if (left == null) {
			left = (Literal) leftResult;
		}

		if (right == null) {
			right = (Literal) rightResult;
		}

		if (left.getType() == Token.STRING && right.getType() == Token.NUMBER ||
				left.getType() == Token.NUMBER && right.getType() == Token.STRING) {
			throw new RestQLException("cannot compare a string with a number");
		}

		int cmp = left.compareTo(right);

		switch (acceptor.getOperator()) {
			case EQUAL:
				result = cmp == 0 ? Literal.trueLiteral() : Literal.falseLiteral();
				break;
			case NOT_EQUAL:
				result = cmp != 0 ? Literal.trueLiteral() : Literal.falseLiteral();
				break;
			case LESS:
				result = cmp < 0 ? Literal.trueLiteral() : Literal.falseLiteral();
				break;
			case LESS_OR_EQUAL:
				result = cmp <= 0 ? Literal.trueLiteral() : Literal.falseLiteral();
				break;
			case GREATER:
				result = cmp > 0 ? Literal.trueLiteral() : Literal.falseLiteral();
				break;
			case GREATER_OR_EQUAL:
				result = cmp >= 0 ? Literal.trueLiteral() : Literal.falseLiteral();
				break;
		}
	}

	public void visit(Call acceptor) {
	}

	public void visit(Member acceptor) {
		acceptor.getTarget()
				  .accept(this);
		Identifier t = (Identifier) result;
		acceptor.getExpression()
				  .accept(this);
		Identifier e = (Identifier) result;

		result = new Identifier(t.getName() + "." + e.getName());
	}

	public void visit(Sequence sequence) {
	}

}
