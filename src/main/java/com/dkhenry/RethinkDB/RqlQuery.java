package com.dkhenry.RethinkDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.core.IsInstanceOf;

import com.rethinkdb.Ql2.Term;
import com.rethinkdb.Ql2.Term.TermType;

abstract public class RqlQuery {
	protected ArrayList<RqlQuery> _args = new ArrayList<RqlQuery>();
	protected HashMap<String,Object> _optargs = new HashMap<String,Object>(); 
	
	public RqlQuery() {		
	}
	
	public RqlQuery(Object... args) {		
		
	}
	
	protected void construct(Object[] args) {
		for(Object o: args) {			
			_args.add(eval(o));
		}		 			
	}
	
	abstract protected Term.TermType tt() ;
	
/*	public static RqlQuery eval(RqlQuery v) { 
		return v;
	}
	public static <T> RqlQuery eval(List<T> l) {
		return new MakeArray(l);
	}
	public static <V extends RqlQuery> RqlQuery eval(Map<String,V> m) {
		return new MakeObject(m);
	}
*/
	public static <T> RqlQuery eval(T t) {
		if( t instanceof RqlQuery) { 
			return (RqlQuery)t;
		}
		if (t instanceof List) {
			return new MakeArray((List)t);
		}
		if (t instanceof Map) {			 		
			return new MakeObject((Map)t);
		}
		return new RqlQuery.Datum(t);
	}
	
	public Term build() { 
		Term.Builder t = Term.newBuilder()
							.setType(tt());
		for(RqlQuery q: _args) { 
			t.addArgs(q.build());
		}
		
		for(Entry<String,Object> e: _optargs.entrySet()) {
			t.addOptargs(
					Term.AssocPair.newBuilder()
						.setKey(e.getKey())
						.setVal(eval(e.getValue()).build())						
						.build()
			);
		}
		return t.build();
	}
		
	public static class Datum extends RqlQuery {
		private Object _data; 
		public <T> Datum(T t) {
			super();			
			_data = t; 
		}
		
		@Override
		protected TermType tt() {			
			return Term.TermType.DATUM;
		}
		
		@Override
		public Term build() { 
			Term.Builder t = Term.newBuilder()
								.setType(tt());
			t.setDatum(com.dkhenry.RethinkDB.Datum.datum(_data));
			return t.build();
		}
	}
	
	public static class MakeArray extends RqlQuery { 		
		public <T> MakeArray(List<T> l) {
			super();			
			for(T t: l) { 
				_args.add(eval(t));
			}
		}
		
		@Override
		protected TermType tt() {			
			return Term.TermType.MAKE_ARRAY;
		}
	}
	
	public static class MakeObject extends RqlQuery { 		
		public <V extends RqlQuery> MakeObject(Map<String,V> m) {
			super();			
			_optargs.putAll(m);			
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.MAKE_OBJ;
		}
	}
	
	public static class Var extends RqlQuery {		
		@Override
		protected TermType tt() {			
			return Term.TermType.VAR;
		}
	}
	
	public static class Default extends RqlQuery {		
		@Override
		protected TermType tt() {			
			return Term.TermType.DEFAULT;
		}
	}
	
	public static class ImplicitVar extends RqlQuery {		
		@Override
		protected TermType tt() {			
			return Term.TermType.IMPLICIT_VAR;
		}
	}

	public static class Not extends RqlQuery {		
		@Override
		protected TermType tt() {			
			return Term.TermType.NOT;
		}
	}
	
	public static class Slice extends RqlQuery {		
		@Override
		protected TermType tt() {			
			return Term.TermType.SLICE;
		}
	}
	
	public static class GetAttr extends RqlQuery {
		@Override
		protected TermType tt() {			
			return Term.TermType.GETATTR;
		}
	}
	
	public static class FunCall extends RqlQuery {		
		@Override
		protected TermType tt() {			
			return Term.TermType.FUNCALL;
		}
	}
	
	public static class Table extends RqlQuery {
		public Table(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.TABLE;
		}
		
		public RqlMethodQuery.Insert insert(Object... args) { 
			Object[] o = new Object[args.length+1];
			o[0] = this;
			System.arraycopy(args,0,o,1,args.length);
			RqlMethodQuery.Insert rvalue =  new RqlMethodQuery.Insert();
			rvalue.construct(o);
			return rvalue;
		}
	}
	
	public static class Nth extends RqlQuery {		
		@Override
		protected TermType tt() {			
			return Term.TermType.NTH;
		}
	}
	
	public static class Match extends RqlQuery {		
		@Override
		protected TermType tt() {			
			return Term.TermType.MATCH;
		}
	}	
	
	public static class Func extends RqlQuery {		
		@Override
		protected TermType tt() {			
			return Term.TermType.FUNC;
		}
	}
}
