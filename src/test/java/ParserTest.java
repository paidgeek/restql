import com.moybl.restql.Lexer;
import com.moybl.restql.Parser;
import com.moybl.restql.RestQLLexer;
import com.moybl.restql.RestQLParser;
import com.moybl.restql.ast.AstNode;
import org.junit.Test;

public class ParserTest {

	@Test
	public void test() {
		Lexer lexer = new RestQLLexer(LexerTest.class.getResourceAsStream("test1.txt"));
		Parser parser = new RestQLParser();

		AstNode result = parser.parse(lexer);

		result.accept(new DumpVisitor());
	}

}
