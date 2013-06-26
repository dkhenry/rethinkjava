package com.dkhenry.RethinkDB;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.ParameterizedType;
import com.dkhenry.RethinkDB.errors.RqlDriverException;

public class RqlObject {

	private com.rethinkdb.Ql2.Datum _under;
	public RqlObject(com.rethinkdb.Ql2.Datum d) {
		_under = d;
	}	
	
	public boolean getBoolean() throws RqlDriverException {
		return getAs();		
	}
	
	public double getNumber() throws RqlDriverException { 
		return getAs();
	}
	
	public String getString() throws RqlDriverException { 
		return getAs();
	}
	
	public List<RqlObject> getList() throws RqlDriverException {
		return getAs();
	}
	
	public Map<String,RqlObject> getObject() throws RqlDriverException {
		return getAs(); 
	}
	
	public <T> T getAs() throws RqlDriverException {
		Class cls = ((Class) ((ParameterizedType)getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0]);
		if (cls == String.class) { 
			return (T) _under.getRStr();
		} else if ( cls == Double.class ) {
			return (T) new Double(_under.getRNum());
		} else if (cls == boolean.class ) {
			return (T) new Boolean(_under.getRBool());
		} else { 
			throw new RqlDriverException("RqlObjects do not support type " + cls.toString());			
		}		
	}
	
	public <T> T getAsOrElse(T orElse) {
		try { 
			return getAs();
		} catch (RqlDriverException ex) { 
			return orElse;
		}		
	}
	
	

}
