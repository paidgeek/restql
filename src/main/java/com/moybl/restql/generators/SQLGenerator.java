package com.moybl.restql.generators;

import com.moybl.restql.ast.*;

public class SQLGenerator implements Visitor {

	private StringBuilder query;

	public SQLGenerator() {
		query = new StringBuilder();
	}

	public void visit(Literal acceptor) {
		if (acceptor.getType() == Literal.Type.STRING) {
			String str = acceptor.getValue().toString();

			str = str.replaceAll("\\*", "%");

			query.append(str);
		} else {
			query.append(acceptor.getValue());
		}
	}

	public void visit(Identifier acceptor) {
		query.append("`" + acceptor.getName() + "`");
	}

	public void visit(UnaryOperation acceptor) {
		acceptor.accept(this);
	}

	public void visit(BinaryOperation acceptor) {
		query.append('(');
		acceptor.getLeft().accept(this);

		switch (acceptor.getOperator()) {
			case AND:
				query.append(" AND ");
				break;
			case OR:
				query.append(" OR ");
				break;
			case GREATER:
				query.append(">");
				break;
			case LESS:
				query.append("<");
				break;
			case GREATER_OR_EQUAL:
				query.append(">=");
				break;
			case LESS_OR_EQUAL:
				query.append("<=");
				break;
			case EQUAL:
				query.append("=");
				break;
			case NOT_EQUAL:
				query.append("!=");
				break;
			case LIKE:
				query.append(" LIKE ");
				break;
		}

		acceptor.getRight().accept(this);
		query.append(')');
	}

	public String getResult() {
		return query.toString();
	}

}
