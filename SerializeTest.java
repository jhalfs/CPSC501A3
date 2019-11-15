import org.junit.Test;
import static org.junit.Assert.*;

import org.jdom2.*;

//Tests assume that inputs are 0,0,0
//This test class tests the Serialize class on an array input to check some of the variables

public class SerializeTest {
	Serialize serTest = new Serialize();
	SimpleArrayClass sac = new SimpleArrayClass(0,0,0);
	
	Element root = new Element("test");
	Class testClass = sac.getClass();
	int inputLength = 3;
	
	Class actualClass;
	Object actualObject;
	int actualLength;

	@Test
	public void testVariables(){
		serTest.serialize(sac, root);
		
		//Check that array length is properly set
		actualLength = serTest.length;
		assertEquals(inputLength, actualLength);
		
		//Check that the serializer gets the correct class
		actualClass = serTest.clazz;
		assertEquals(testClass, actualClass);
		
		//Test the object is correct
		actualObject = serTest.obj;
		assertEquals(sac, actualObject);
	}
	

}
