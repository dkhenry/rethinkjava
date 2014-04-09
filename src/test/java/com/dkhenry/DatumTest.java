package com.dkhenry;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;

import com.dkhenry.RethinkDB.RqlMethodQuery;
import com.rethinkdb.Ql2;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.dkhenry.RethinkDB.Datum;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

public class DatumTest {
	
	@Test(groups={"acceptance"})
	public void testDatumNull() {
		byte[] expected = com.rethinkdb.Ql2.Datum.newBuilder().setType(com.rethinkdb.Ql2.Datum.DatumType.R_NULL).build().toByteArray();
		byte[] actual = Datum.datum().toByteArray();
		AssertJUnit.assertArrayEquals(expected, actual);
	}
	
	@Test(groups={"acceptance"})
	public void testDatumBool() {
		byte[] expected = com.rethinkdb.Ql2.Datum.newBuilder().setType(com.rethinkdb.Ql2.Datum.DatumType.R_BOOL).setRBool(true).build().toByteArray();
		byte[] actual = Datum.datum(true).toByteArray();
		AssertJUnit.assertArrayEquals(expected, actual);
		
		actual = Datum.datum(Boolean.valueOf(true)).toByteArray();
		AssertJUnit.assertArrayEquals(expected, actual);
	}
	
	@Test(groups={"acceptance"})
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
	
	@Test(groups={"acceptance"})
	public void testDatumString() {
		byte[] expected = com.rethinkdb.Ql2.Datum.newBuilder().setType(com.rethinkdb.Ql2.Datum.DatumType.R_STR).setRStr("SuperAwesomeTest").build().toByteArray();
		byte[] actual = Datum.datum("SuperAwesomeTest").toByteArray();
		
		AssertJUnit.assertArrayEquals(expected, actual);
	}
	
	@Test(groups={"acceptance"})
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
	
	@Test(groups={"acceptance"})
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
	
	@Test(groups={"acceptance"})
	public void testDatumArrayNested() { 
		HashMap h = new HashMap() {{ put("SuperAwesomeKey",true); }};
		
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
		d.addRArrayBuilder()
			.setType(com.rethinkdb.Ql2.Datum.DatumType.R_OBJECT)
		.addRObjectBuilder()
			.setKey("SuperAwesomeKey")
			.setVal(com.rethinkdb.Ql2.Datum.newBuilder().setType(com.rethinkdb.Ql2.Datum.DatumType.R_BOOL).setRBool(true));
		d.addRArrayBuilder()
			.setType(com.rethinkdb.Ql2.Datum.DatumType.R_NULL);
					
		byte[] expected = d.build().toByteArray();				
		byte[] actual = Datum.datum(Arrays.asList(true,1.0,"SuperAwesomeTest",Arrays.asList(true),h,null)).toByteArray();
		
		AssertJUnit.assertArrayEquals(expected, actual);
		
	}
	
	@Test(groups={"acceptance"})
	public void testDatumObject() { 
		com.rethinkdb.Ql2.Datum.Builder d =  com.rethinkdb.Ql2.Datum.newBuilder().setType(com.rethinkdb.Ql2.Datum.DatumType.R_OBJECT);
		d.addRObjectBuilder()
			.setKey("SuperAwesomeKey")
			.setVal(com.rethinkdb.Ql2.Datum.newBuilder().setType(com.rethinkdb.Ql2.Datum.DatumType.R_BOOL).setRBool(true));		
		byte[] expected = d.build().toByteArray();				
		HashMap h = new HashMap() {{ put("SuperAwesomeKey",true); }};
		com.rethinkdb.Ql2.Datum datum = Datum.datum( h );
		byte[] actual = datum.toByteArray();
		
		AssertJUnit.assertArrayEquals(expected, actual);
	}

    @Test(groups={"acceptance"})
    public void testInsertBuilder() {
        com.rethinkdb.Ql2.Term.Builder term = com.rethinkdb.Ql2.Term.newBuilder();
        term.setType(Ql2.Term.TermType.INSERT);
        term.addArgsBuilder()
                .setType(Ql2.Term.TermType.MAKE_OBJ)
                .addOptargsBuilder()
                    .setKey("SuperAwesomeKey")
                    .setVal(Ql2.Term.newBuilder()
                                .setType(Ql2.Term.TermType.DATUM)
                                .setDatum(Ql2.Datum.newBuilder()
                                    .setType(Ql2.Datum.DatumType.R_BOOL)
                                    .setRBool(true)
                                )
                    );
        term.addOptargsBuilder()
                .setKey("durability")
                .setVal(Ql2.Term.newBuilder()
                    .setType(Ql2.Term.TermType.DATUM)
                    .setDatum(Ql2.Datum.newBuilder()
                        .setType(Ql2.Datum.DatumType.R_STR)
                        .setRStr("hard")
                    )
                );
        term.addOptargsBuilder()
                .setKey("returnVals")
                .setVal(Ql2.Term.newBuilder()
                        .setType(Ql2.Term.TermType.DATUM)
                        .setDatum(Ql2.Datum.newBuilder()
                                .setType(Ql2.Datum.DatumType.R_BOOL)
                                .setRBool(false)
                        )
                );
        term.addOptargsBuilder()
                .setKey("upsert")
                .setVal(Ql2.Term.newBuilder()
                        .setType(Ql2.Term.TermType.DATUM)
                        .setDatum(Ql2.Datum.newBuilder()
                                .setType(Ql2.Datum.DatumType.R_BOOL)
                                .setRBool(false)
                        )
                );

        RqlMethodQuery.Insert t = new RqlMethodQuery.Insert(
                new HashMap() {{ put("SuperAwesomeKey",true); }},
                new HashMap() {{ put("durability", "hard"); put("returnVals",false); put("upsert",false); }}
        );
        byte[] expected = term.build().toByteArray();
        byte[] actual = t.build().toByteArray();
        AssertJUnit.assertArrayEquals(expected, actual);
    }
}
