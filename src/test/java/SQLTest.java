import com.moybl.restql.*;
import org.junit.*;

import java.io.*;

public class SQLTest {

	@Test
	public void simpleQuery() {
		InputStream is = LexerTest.class.getResourceAsStream("test2.txt");

		Assert.assertEquals("((`x`>18) AND ((`name` LIKE '%a%') OR (`date`>'2006-05-00')))", RestQL.parseToSQL(is));
	}

}
