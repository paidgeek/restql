import com.moybl.restql.RestQL;
import com.moybl.restql.generators.SQLGenerator;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SQLGeneratorTest {

	@Test
	public void simpleSelect() {
		SQLGenerator sqlGenerator = new SQLGenerator();
		RestQL.parse("select(name, email) & from(user) & where(user.age >: 18)")
				.accept(sqlGenerator);

		assertEquals("SELECT name, email FROM user WHERE (user.age >= 18)", sqlGenerator.getResult());
	}

	@Test
	public void complexSelect() {
		SQLGenerator sqlGenerator = new SQLGenerator();
		RestQL.parse("select(name,email)&from(user)&join(post,user.id:post.userId)&where(user.age>:18 and post.likes>:1000)")
				.accept(sqlGenerator);

		assertEquals("SELECT name, email FROM user JOIN post ON((user.id = post.userId)) WHERE ((user.age >= 18) AND (post.likes >= 1000))", sqlGenerator.getResult());
	}

}
