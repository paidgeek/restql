import com.moybl.restql.Lexer;
import com.moybl.restql.RestQLLexer;
import com.moybl.restql.Symbol;
import com.moybl.restql.Token;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class LexerTest {

	@Test
	public void parseQuery() {
		Lexer lexer = new RestQLLexer(new ByteArrayInputStream("a.x=42&y=1,2,3&z=val!:'hi'and or".getBytes()));

		Symbol[] symbols = new Symbol[]{
				new Symbol(Token.IDENTIFIER, "a"),
				new Symbol(Token.DOT, "."),
				new Symbol(Token.IDENTIFIER, "x"),
				new Symbol(Token.ASSIGNMENT, "="),
				new Symbol(Token.NUMBER, "42"),
				new Symbol(Token.AMPERSAND, "&"),
				new Symbol(Token.IDENTIFIER, "y"),
				new Symbol(Token.ASSIGNMENT, "="),
				new Symbol(Token.NUMBER, "1"),
				new Symbol(Token.COMMA, ","),
				new Symbol(Token.NUMBER, "2"),
				new Symbol(Token.COMMA, ","),
				new Symbol(Token.NUMBER, "3"),
				new Symbol(Token.AMPERSAND, "&"),
				new Symbol(Token.IDENTIFIER, "z"),
				new Symbol(Token.ASSIGNMENT, "="),
				new Symbol(Token.IDENTIFIER, "val"),
				new Symbol(Token.NOT_EQUAL, "!:"),
				new Symbol(Token.STRING, "'hi'"),
				new Symbol(Token.AND, "and"),
				new Symbol(Token.OR, "or")
		};

		for (int i = 0; i < symbols.length; i++) {
			Assert.assertEquals(symbols[i], lexer.next());
		}
	}

}
