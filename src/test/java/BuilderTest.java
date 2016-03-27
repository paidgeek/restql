import com.moybl.restql.Token;
import com.moybl.restql.factory.RestQLBuilder;
import com.moybl.restql.factory.SourceFactory;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class BuilderTest {

	@Test
	public void simple() {
		RestQLBuilder b = new RestQLBuilder();

		assertEquals("print('hi')&life=42&x=1!:2", SourceFactory.build(b.call(b.identifier("print"), b.literal("hi"))
																							 .assignment(b.identifier("life"), b.literal(42))
																							 .assignment(b.identifier("x"), b.binaryOperation(b.literal(1), Token.NOT_EQUAL, b.literal(2)))
																							 .build()));
	}

}
