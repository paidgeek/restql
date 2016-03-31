import com.moybl.restql.*;
import com.moybl.restql.ast.*;

import org.junit.*;

public class ParserTest {

	@Test
	public void test() {
		AstNode result = RestQL.parse("print('hello, world')&life=42&nope=1!:2");

		result.accept(new DumpVisitor());
	}

	@Test
	public void sequence() {
		AstNode result = RestQL.parse("user_name,password,salt");

		result.accept(new DumpVisitor());
	}

}
