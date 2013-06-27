package com.dkhenry.RethinkDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dkhenry.RethinkDB.errors.RqlDriverException;

public class Datum {
	/* Datum Constructors */
	/* We will specialize for all types defined in the protocol */
    public static com.rethinkdb.Ql2.Datum datum(Boolean b) {    	
    	return com.rethinkdb.Ql2.Datum.newBuilder()
				.setType(com.rethinkdb.Ql2.Datum.DatumType.R_BOOL)
				.setRBool(b)
				.build();
    }
    public static com.rethinkdb.Ql2.Datum datum(String s) {
    	return com.rethinkdb.Ql2.Datum.newBuilder()
				.setType(com.rethinkdb.Ql2.Datum.DatumType.R_STR)
				.setRStr(s)
				.build();
    }
    public static com.rethinkdb.Ql2.Datum datum(Double d) {
    	return com.rethinkdb.Ql2.Datum.newBuilder()
				.setType(com.rethinkdb.Ql2.Datum.DatumType.R_NUM)
				.setRNum(d)
				.build();
    }
    
    /* We want to cast all "Numbers" to Doubles */
    public static<T extends Number> com.rethinkdb.Ql2.Datum datum(T n) {
    	return datum(new Double(n.doubleValue()));
    }
    /* For any type we haven't specialized we are going to cast to a string */ 
    public static <T> com.rethinkdb.Ql2.Datum datum(T t) { 
    	return datum(t.toString());
    }
    
    // The R Array
    public static <T> com.rethinkdb.Ql2.Datum datum(List<T> a) {
    	com.rethinkdb.Ql2.Datum.Builder b =  com.rethinkdb.Ql2.Datum.newBuilder()
    			.setType(com.rethinkdb.Ql2.Datum.DatumType.R_ARRAY);
    	for(T value: a) {
    		b.addRArray(datum(value));
    	}
    	return b.build();
    }
    
    // This is the R_OBJECT
    public static <K,V> com.rethinkdb.Ql2.Datum datum(Map<K,V> h){
    	com.rethinkdb.Ql2.Datum.Builder b = com.rethinkdb.Ql2.Datum.newBuilder()
    			.setType(com.rethinkdb.Ql2.Datum.DatumType.R_OBJECT);
    	for(Entry<K, V> entry: h.entrySet()) {
    		b.addRObject(
    				com.rethinkdb.Ql2.Datum.AssocPair.newBuilder()
    					.setKey(entry.getKey().toString())
    					.setVal(datum(entry.getValue()))
    					.build()
    				);
    	}
    	return b.build();
    }
    
    public static Object deconstruct(com.rethinkdb.Ql2.Datum d) throws RqlDriverException {
    	switch(d.getType()) { 
    	case R_NULL:
    		return null;
    	case R_BOOL:
    		return Boolean.valueOf(d.getRBool());
    	case R_NUM:
    		return Double.valueOf(d.getRNum());
    	case R_STR:
    		return d.getRStr();
    	case R_ARRAY:
    		ArrayList<Object> l = new ArrayList<Object>(); 
    		for(com.rethinkdb.Ql2.Datum datum :d.getRArrayList()) {
    			l.add(deconstruct(datum));
    		}
    		return l;
    	case R_OBJECT:
    		HashMap<String,Object> m = new HashMap<String, Object>();
    		for(com.rethinkdb.Ql2.Datum.AssocPair ap :d.getRObjectList()) {
    			m.put(ap.getKey(),deconstruct(ap.getVal()));
    		}
    		return m;
    	default:
    		throw new RqlDriverException("Unknown Dataum Type " + d.getType().toString() + " presented for Deconstruction") ;
    	}
    }
}
