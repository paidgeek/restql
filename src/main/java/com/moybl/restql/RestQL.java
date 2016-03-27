package com.moybl.restql;

import com.moybl.restql.ast.Query;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class RestQL {

	public static Query parse(String source) {
		return parse(new ByteArrayInputStream(source.getBytes()));
	}

	public static Query parse(InputStream inputStream) {
		return new RestQLParser().parse(new RestQLLexer(inputStream));
	}

}
