import com.moybl.restql.*;
import org.junit.*;

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
