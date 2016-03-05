package com.moybl.restql;

import com.moybl.restql.ast.*;

public interface Parser {

	AstNode parse(Lexer lexer);

}
