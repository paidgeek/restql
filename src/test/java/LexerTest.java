import com.moybl.restql.Lexer;
import com.moybl.restql.RestQLLexer;
import com.moybl.restql.Symbol;
import com.moybl.restql.Token;
import org.junit.Assert;
import org.junit.Test;

public class LexerTest {

	@Test
	public void parseFile() {
		Lexer lexer = new RestQLLexer(LexerTest.class.getResourceAsStream("test1.txt"));

		Symbol[] symbols = new Symbol[]{
				new Symbol(Token.IDENTIFIER, "x"),
				new Symbol(Token.NOT_EQUAL, "!:"),
				new Symbol(Token.INTEGER, "42"),
		};

		for (int i = 0; i < symbols.length; i++) {
			Assert.assertEquals(symbols[i], lexer.next());
		}
	}

}
