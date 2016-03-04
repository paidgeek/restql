import com.moybl.restql.*;
import com.moybl.restql.ast.AstNode;
import com.moybl.restql.generators.SQLGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

public class SQLTest {

	@Test
	public void simpleQuery() {
		InputStream is = LexerTest.class.getResourceAsStream("test2.txt");

		Assert.assertEquals("((`x`>18) AND ((`name` LIKE '%a%') OR (`date`>'2006-05-00')))", RestQL.parseToSQL(is));
	}

}
