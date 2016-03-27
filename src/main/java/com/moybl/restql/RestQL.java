package com.moybl.restql;

import com.moybl.restql.ast.AstNode;

import java.io.InputStream;

public class RestQL {

	public static AstNode parse(InputStream inputStream) {
		return new RestQLParser().parse(new RestQLLexer(inputStream));
	}

}
