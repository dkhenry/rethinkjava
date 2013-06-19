package com.dkhenry.RethinkDB;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
}
