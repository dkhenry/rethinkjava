package com.dkhenry;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;

import com.dkhenry.RethinkDB.Datum;;

public class DatumTest {
	
	@Test
	public void testDatumBool() {
		byte[] expected = com.rethinkdb.Ql2.Datum.newBuilder().setType(com.rethinkdb.Ql2.Datum.DatumType.R_BOOL).setRBool(true).build().toByteArray();
		byte[] actual = Datum.datum(true).toByteArray();
		AssertJUnit.assertArrayEquals(expected, actual);
		
		actual = Datum.datum(Boolean.valueOf(true)).toByteArray();
		AssertJUnit.assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testDatumNumber() { 
		byte[] expected = com.rethinkdb.Ql2.Datum.newBuilder().setType(com.rethinkdb.Ql2.Datum.DatumType.R_NUM).setRNum(1.0).build().toByteArray();
		byte[] actual = Datum.datum(1.0).toByteArray();
		AssertJUnit.assertArrayEquals(expected, actual);
		
		// int
		actual = Datum.datum(1).toByteArray() ;
		AssertJUnit.assertArrayEquals(expected, actual);
		
		// Integer
		actual = Datum.datum(Integer.valueOf(1)).toByteArray() ;
		AssertJUnit.assertArrayEquals(expected, actual);
		
		// long
		actual = Datum.datum(1L).toByteArray();
		AssertJUnit.assertArrayEquals(expected, actual);
		
		// Long
		actual = Datum.datum(Long.valueOf(1L)).toByteArray();
		AssertJUnit.assertArrayEquals(expected, actual);
		
		// BigInteger 
		actual = Datum.datum(BigInteger.valueOf(1L)).toByteArray();
		AssertJUnit.assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testDatumString() {
		byte[] expected = com.rethinkdb.Ql2.Datum.newBuilder().setType(com.rethinkdb.Ql2.Datum.DatumType.R_STR).setRStr("SuperAwesomeTest").build().toByteArray();
		byte[] actual = Datum.datum("SuperAwesomeTest").toByteArray();
		
		AssertJUnit.assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testDatumArraySimple() { 
		com.rethinkdb.Ql2.Datum.Builder d =  com.rethinkdb.Ql2.Datum.newBuilder().setType(com.rethinkdb.Ql2.Datum.DatumType.R_ARRAY);
		d.addRArrayBuilder()
			.setType(com.rethinkdb.Ql2.Datum.DatumType.R_BOOL)
			.setRBool(true);
		d.addRArrayBuilder()
			.setType(com.rethinkdb.Ql2.Datum.DatumType.R_BOOL)
			.setRBool(false);
		
		byte[] expected = d.build().toByteArray();		
		byte[] actual = Datum.datum(Arrays.asList(true,false)).toByteArray();
		
		AssertJUnit.assertArrayEquals(expected, actual);	
	}
	
	@Test
	public void testDatumArrayMixed() { 
		com.rethinkdb.Ql2.Datum.Builder d =  com.rethinkdb.Ql2.Datum.newBuilder().setType(com.rethinkdb.Ql2.Datum.DatumType.R_ARRAY);
		d.addRArrayBuilder()
			.setType(com.rethinkdb.Ql2.Datum.DatumType.R_BOOL)
			.setRBool(true);
		d.addRArrayBuilder()
			.setType(com.rethinkdb.Ql2.Datum.DatumType.R_NUM)
			.setRNum(1.0);
		d.addRArrayBuilder()
			.setType(com.rethinkdb.Ql2.Datum.DatumType.R_STR)
			.setRStr("SuperAwesomeTest");
		
		byte[] expected = d.build().toByteArray();				
		byte[] actual = Datum.datum(Arrays.asList(true,1.0,"SuperAwesomeTest")).toByteArray();
		
		AssertJUnit.assertArrayEquals(expected, actual);	
	}
	
	@Test
	public void testDatumArrayNested() { 
		com.rethinkdb.Ql2.Datum.Builder d =  com.rethinkdb.Ql2.Datum.newBuilder().setType(com.rethinkdb.Ql2.Datum.DatumType.R_ARRAY);
		d.addRArrayBuilder()
			.setType(com.rethinkdb.Ql2.Datum.DatumType.R_BOOL)
			.setRBool(true);
		d.addRArrayBuilder()
			.setType(com.rethinkdb.Ql2.Datum.DatumType.R_NUM)
			.setRNum(1.0);
		d.addRArrayBuilder()
			.setType(com.rethinkdb.Ql2.Datum.DatumType.R_STR)
			.setRStr("SuperAwesomeTest");
		d.addRArrayBuilder()
		.setType(com.rethinkdb.Ql2.Datum.DatumType.R_ARRAY)
		.addRArrayBuilder()
			.setType(com.rethinkdb.Ql2.Datum.DatumType.R_BOOL)
			.setRBool(true);
		
					
		byte[] expected = d.build().toByteArray();				
		byte[] actual = Datum.datum(Arrays.asList(true,1.0,"SuperAwesomeTest",Arrays.asList(true))).toByteArray();
		
		AssertJUnit.assertArrayEquals(expected, actual);
		
	}
	
	@Test
	public void testDatumObject() { 
		com.rethinkdb.Ql2.Datum.Builder d =  com.rethinkdb.Ql2.Datum.newBuilder().setType(com.rethinkdb.Ql2.Datum.DatumType.R_OBJECT);
		d.addRObjectBuilder()
			.setKey("SuperAwesomeKey")
			.setVal(com.rethinkdb.Ql2.Datum.newBuilder().setType(com.rethinkdb.Ql2.Datum.DatumType.R_BOOL).setRBool(true));		
		byte[] expected = d.build().toByteArray();				
		byte[] actual = Datum.datum( (new HashMap() {{ put("SuperAwesomeKey",true); }}) ).toByteArray();
		
		AssertJUnit.assertArrayEquals(expected, actual);
	}	
}
