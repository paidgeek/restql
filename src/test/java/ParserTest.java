import com.moybl.restql.*;
import com.moybl.restql.ast.*;

import org.junit.*;

public class ParserTest {

	@Test
	public void test() {
		Lexer lexer = new RestQLLexer(LexerTest.class.getResourceAsStream("test1.txt"));
		Parser parser = new RestQLParser();

		AstNode result = parser.parse(lexer);

		result.accept(new DumpVisitor());
	}

}
