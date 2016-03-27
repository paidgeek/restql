package com.moybl.restql.ast;

import java.util.List;

public class Query extends AstNode {

	private List<Assignment> assignments;

	public Query(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	@Override
	public String toString() {
		return "Query";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
