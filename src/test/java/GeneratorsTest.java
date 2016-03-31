import com.moybl.restql.RestQL;
import com.moybl.restql.ast.Query;
import com.moybl.restql.generators.SQLGenerator;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GeneratorsTest {

	@Test
	public void simpleSelect() {
		SQLGenerator sql = new SQLGenerator();

		Query query = RestQL.parse("select(name, email) & from(user) & where(user.age >: 18)");

		query.accept(sql);

		assertEquals("SELECT name, email FROM user WHERE (user.age >= 18)", sql.getResult());
	}

	@Test
	public void complexSelect() {
		SQLGenerator sqlGenerator = new SQLGenerator();
		RestQL.parse("select(name,email)&from(user)&join(post,user.id:post.userId)&where(user.age>:18 and post.likes>:1000)")
				.accept(sqlGenerator);

		assertEquals("SELECT name, email FROM user JOIN post ON((user.id = post.userId)) WHERE ((user.age >= 18) AND (post.likes >= 1000))", sqlGenerator.getResult());
	}

}
