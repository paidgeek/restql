import com.moybl.restql.*;
import com.moybl.restql.ast.*;

import org.junit.*;

public class ParserTest {

	@Test
	public void test() {
		AstNode result = RestQL.parse("a.x=b.f(42,'hi')&42&'hello, world'");

		result.accept(new DumpVisitor());
	}

}
