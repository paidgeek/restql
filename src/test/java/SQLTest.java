import com.moybl.restql.Lexer;
import com.moybl.restql.Parser;
import com.moybl.restql.RestQLLexer;
import com.moybl.restql.RestQLParser;
import com.moybl.restql.ast.AstNode;
import com.moybl.restql.generators.SQLGenerator;
import org.junit.Assert;
import org.junit.Test;

public class SQLTest {

	@Test
	public void simpleQuery() {
		Lexer lexer = new RestQLLexer(LexerTest.class.getResourceAsStream("test2.txt"));
		Parser parser = new RestQLParser();

		AstNode result = parser.parse(lexer);

		SQLGenerator sqlGenerator = new SQLGenerator();
		result.accept(sqlGenerator);

		Assert.assertEquals("((`age`>18) AND ((`name` LIKE '%a%') OR (`created_at`>'2006-05-00')))", sqlGenerator.getResult());
	}

}
