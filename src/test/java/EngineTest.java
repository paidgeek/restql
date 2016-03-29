import com.moybl.restql.Engine;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class EngineTest {

	private Engine e;

	@Before
	public void setup() {
		e = new Engine();
	}

	@Test
	public void not() {
		e.execute("x=!1");

		assertEquals(0.0, e.getVariable("x")
								 .numberValue());
	}

	@Test
	public void strings() {
		e.execute("s = 'hi' & s1 = s");

		assertEquals("hi", e.getVariable("s")
								  .stringValue());
		assertEquals("hi", e.getVariable("s1")
								  .stringValue());
	}

	@Test
	public void expr() {
		e.execute("a = 1 & b = 2 & c = a < b and b : 2");

		assertEquals(1.0, e.getVariable("c")
								 .numberValue());
	}

	@Test
	public void members() {
		e.execute("p.c = 42");

		assertEquals(42.0, e.getVariable("p.c")
								  .numberValue());
	}

}
