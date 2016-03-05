package com.moybl.restql;

import com.moybl.restql.generators.*;

import java.io.*;

public class RestQL {

	public static String parseToSQL(String source) {
		InputStream is = new ByteArrayInputStream(source.getBytes());

		return parseToSQL(is);
	}

	public static String parseToSQL(InputStream inputStream) {
		SQLGenerator sqlGenerator = new SQLGenerator();

		new RestQLParser().parse(new RestQLLexer(inputStream)).accept(sqlGenerator);

		return sqlGenerator.getResult();
	}

}
