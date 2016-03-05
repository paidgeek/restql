import com.moybl.restql.*;
import org.junit.*;

public class ErrorTest {

	@Test
	public void errors() {
		try {
			RestQL.parseToSQL("x-x");
			Assert.fail();
		} catch (RestQLException e) {
			Assert.assertEquals("Illegal character '-'", e.getMessage());
		}
	}

}
