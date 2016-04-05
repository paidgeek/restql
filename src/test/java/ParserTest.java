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

	@Test
	public void emptyArguments() {
		AstNode result = RestQL.parse("f()");

		result.accept(new DumpVisitor());
	}

	@Test
	public void chainedCalls() {
		AstNode result = RestQL.parse("f(1).g(2).h(3)");

		result.accept(new DumpVisitor());
	}

}
