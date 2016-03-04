package com.moybl.restql;

import com.moybl.restql.ast.AstNode;

public interface Parser {

	AstNode parse(Lexer lexer);

}
