package com.dkhenry.RethinkDB;

import java.lang.reflect.Constructor;

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
	public static class Json extends RqlTopLevelQuery {
		public Json(Object ...args) { 
			construct(args);
		}
		
		@Override
		protected TermType tt() { 
			return Term.TermType.JSON;
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
			return prepend_construct(args, RqlMethodQuery.TableCreate.class);			
		}
		public RqlMethodQuery.TableDrop table_drop(Object... args) {
			return prepend_construct(args, RqlMethodQuery.TableDrop.class);			
		}
		public RqlMethodQuery.TableList table_list(Object... args) {
			return prepend_construct(args, RqlMethodQuery.TableList.class);			
		}
		public RqlQuery.Table table(Object... args) {
			return prepend_construct(args, RqlMethodQuery.Table.class);			
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
			construct_with_optargs(args,1);
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

    public static class Iso8601 extends RqlTopLevelQuery {
        public Iso8601(Object ...args) {
            construct(args);
        }
        @Override
        protected TermType tt() {
            return Term.TermType.ISO8601;
        }
    }

    public static class EpochTime extends RqlTopLevelQuery {
        public EpochTime(Object ...args) {
            construct(args);
        }
        @Override
        protected TermType tt() {
            return Term.TermType.EPOCH_TIME;
        }
    }

    public static class Now extends RqlTopLevelQuery {
        public Now(Object ...args) {
            construct(args);
        }
        @Override
        protected TermType tt() {
            return Term.TermType.NOW;
        }
    }

    public static class Time extends RqlTopLevelQuery {
        public Time(Object ...args) {
            construct(args);
        }
        @Override
        protected TermType tt() {
            return Term.TermType.TIME;
        }
    }

    public static class Literal extends RqlTopLevelQuery {
        public Literal(Object ...args) {
            construct(args);
        }
        @Override
        protected TermType tt() {
            return Term.TermType.LITERAL;
        }
    }
}
