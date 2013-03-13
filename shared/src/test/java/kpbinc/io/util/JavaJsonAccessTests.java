package kpbinc.io.util;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class JavaJsonAccessTests {

	//= Tests ==========================================================================================================
	
	@Test
	public void testFlatObjectAccess() {
		// ARRANGE
		Map<String, Object> object = new TreeMap<String, Object>();
		String propAName = "propA";
		String propAValue = "a";
		String propBName = "propB";
		String propBValue = "b";
		object.put(propAName, propAValue);
		object.put(propBName, propBValue);
		
		// ACT/ASSERT
		assertEquals("value A", propAValue, JavaJsonAccess.getValue(object, propAName));
		assertEquals("value B", propBValue, JavaJsonAccess.getValue(object, propBName));
		assertNull("non-existent value C", JavaJsonAccess.getValue(object, "propC"));
		assertNull("no path", JavaJsonAccess.getValue(object));
		assertNull("too deep", JavaJsonAccess.getValue(object, propAName, propBName));
	}

	@Test
	public void testNestedObjectAccess() {
		// ARRANGE
		Map<String, Object> object = new TreeMap<String, Object>();
		String propAName = "propA";
		String propAValue = "a";
		object.put(propAName, propAValue);
		
		Map<String, Object> nestedObject = new TreeMap<String, Object>();
		String propBName = "propB";
		String propBValue = "b";
		nestedObject.put(propBName, propBValue);
		
		String nestedObjectName = "nestedObject";
		object.put(nestedObjectName, nestedObject);
		
		// ACT
		assertEquals("value A", propAValue, JavaJsonAccess.getValue(object, propAName));
		assertEquals("value B", propBValue, JavaJsonAccess.getValue(object, nestedObjectName, propBName));
		assertNull("too shallow", JavaJsonAccess.getValue(object, propBName));
		assertNull("too deep", JavaJsonAccess.getValue(object, nestedObjectName, propAName));
	}
	
}
