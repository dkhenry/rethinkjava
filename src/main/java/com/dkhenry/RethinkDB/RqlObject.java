package com.dkhenry.RethinkDB;

import java.util.List;
import java.util.Map;
import com.dkhenry.RethinkDB.errors.RqlDriverException;

public class RqlObject {

	private com.rethinkdb.Ql2.Datum _underlying;
	public RqlObject() {
			
	}
	
	public RqlObject(com.rethinkdb.Ql2.Datum d) {
		_underlying = d;
	}	
	
	public boolean getBoolean() throws RqlDriverException {
		return as();		
	}
	
	public double getNumber() throws RqlDriverException { 
		return as();
	}
	
	public String getString() throws RqlDriverException { 
		return as();
	}
	
	public List<Object> getList() throws RqlDriverException {
		return as();
	}
	
	public Map<String,Object> getMap() throws RqlDriverException {
		return as();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T as() throws RqlDriverException {
		return (T) Datum.deconstruct(_underlying);		
	}

	// The next few function will assume this is of type "RObject" 
	@SuppressWarnings("unchecked")
	public <T> T getAs(String key) throws RqlDriverException {
		Map<String,Object> m = as();
		return (T) m.get(key);					
	}
	
	public <T> T getAsOrElse(String key, T orElse) throws RqlDriverException {
		T rval = getAs(key);
		if(null == rval) { 
			return orElse;
		}
		return rval;		
	}
	
	

}
