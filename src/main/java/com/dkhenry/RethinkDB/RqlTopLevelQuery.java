package com.dkhenry.RethinkDB;

import com.rethinkdb.Ql2.Term;
import com.rethinkdb.Ql2.Term.TermType;

public abstract class RqlTopLevelQuery extends RqlQuery {
	public RqlTopLevelQuery() { 
		super();
	}
	public static class JavaScript extends RqlTopLevelQuery {
		public JavaScript(Object ...args) {
			construct(args);
		}
		
		@Override
		protected TermType tt() {			
			return Term.TermType.JAVASCRIPT;
		}
	}
	
	public static class UserError extends RqlTopLevelQuery {
		public UserError(Object ...args) {
			construct(args);
		}
		
		@Override
		protected TermType tt() {			
			return Term.TermType.ERROR;
		}
	}
	
	public static class DB extends RqlTopLevelQuery {	
		public DB(Object... args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.DB;
		}
		
		public RqlMethodQuery.TableCreate table_create(Object... args) {
			RqlMethodQuery.TableCreate rvalue = new RqlMethodQuery.TableCreate();
			Object[] o = new Object[args.length+1];
			o[0] = this;
			System.arraycopy(args,0,o,1,args.length);			
			rvalue.construct(o);						
			return rvalue;
		}
		public RqlMethodQuery.TableDrop table_drop(Object... args) {
			RqlMethodQuery.TableDrop rvalue = new RqlMethodQuery.TableDrop();
			Object[] o = new Object[args.length+1];
			o[0] = this;
			System.arraycopy(args,0,o,1,args.length);			
			rvalue.construct(o);						
			return rvalue;
		}
		public RqlMethodQuery.TableList table_list(Object... args) {
			RqlMethodQuery.TableList rvalue = new RqlMethodQuery.TableList();
			Object[] o = new Object[args.length+1];
			o[0] = this;
			System.arraycopy(args,0,o,1,args.length);			
			rvalue.construct(o);						
			return rvalue;
		}
		public RqlQuery.Table table(Object... args) {
			RqlQuery.Table rvalue = new RqlQuery.Table();
			Object[] o = new Object[args.length+1];
			o[0] = this;
			System.arraycopy(args,0,o,1,args.length);			
			rvalue.construct(o);						
			return rvalue;
		}		
	}
	
	public static class DbCreate extends RqlTopLevelQuery {
		public DbCreate(Object ...args) {
			construct(args);
		}
		
		@Override
		protected TermType tt() {			
			return Term.TermType.DB_CREATE;
		}
	}
	
	public static class DbDrop extends RqlTopLevelQuery {
		public DbDrop(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.DB_DROP;
		}
	}
	
	public static class DbList extends RqlTopLevelQuery {
		public DbList(Object ...args) {
			construct(args);
		}
		
		@Override
		protected TermType tt() {			
			return Term.TermType.DB_LIST;
		}
	}
	
	public static class TableCreateTL extends RqlTopLevelQuery {
		public TableCreateTL(Object ...args) {
			construct(args);
		}
		
		@Override
		protected TermType tt() {			
			return Term.TermType.TABLE_CREATE;
		}
	}
	
	public static class TableDropTL extends RqlTopLevelQuery {
		public TableDropTL(Object ...args) {
			construct(args);
		}
		
		@Override
		protected TermType tt() {			
			return Term.TermType.TABLE_DROP;
		}
	}
	
	public static class TableListTL extends RqlTopLevelQuery {
		public TableListTL(Object ...args) {
			construct(args);
		}
		
		@Override
		protected TermType tt() {			
			return Term.TermType.TABLE_LIST;
		}
	}
	
	public static class Asc extends RqlTopLevelQuery {
		public Asc(Object ...args) {
			construct(args);
		}
		
		@Override
		protected TermType tt() {			
			return Term.TermType.ASC;
		}
	}
	
	public static class Desc extends RqlTopLevelQuery {
		public Desc(Object ...args) {
			construct(args);
		}
		
		@Override
		protected TermType tt() {			
			return Term.TermType.DESC;
		}
	}
	
	public static class Branch extends RqlTopLevelQuery {
		public Branch(Object ...args) {
			construct(args);
		}
		
		@Override
		protected TermType tt() {			
			return Term.TermType.BRANCH;
		}
	}
}
