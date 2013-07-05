package com.dkhenry.RethinkDB;

import com.rethinkdb.Ql2.Term.TermType;
import com.rethinkdb.Ql2.Term;

public abstract class RqlBiOperQuery extends RqlQuery {

	public static class Eq extends RqlBiOperQuery {
		public Eq(Object ...args) {
			construct(args);
		}
		@Override
		public TermType tt() {
			return Term.TermType.EQ ;
		}
	}

	public static class Ne extends RqlBiOperQuery {
		public Ne(Object ...args) {
			construct(args);
		}
		@Override
		public TermType tt() {
			return Term.TermType.NE ;
		}
	}

	public static class Lt extends RqlBiOperQuery {
		public Lt(Object ...args) {
			construct(args);
		}
		@Override
		public TermType tt() {
			return Term.TermType.LT ;
		}
	}

	public static class Le extends RqlBiOperQuery {
		public Le(Object ...args) {
			construct(args);
		}
		@Override
		public TermType tt() {
			return Term.TermType.LE ;
		}
	}

	public static class Gt extends RqlBiOperQuery {
		public Gt(Object ...args) {
			construct(args);
		}
		@Override
		public TermType tt() {
			return Term.TermType.GT ;
		}
	}

	public static class Ge extends RqlBiOperQuery {
		public Ge(Object ...args) {
			construct(args);
		}
		@Override
		public TermType tt() {
			return Term.TermType.GE ;
		}
	}
	public static class Add extends RqlBiOperQuery {
		public Add(Object ...args) { 
			construct(args);
		}
		@Override
		public TermType tt() {
			return Term.TermType.ADD;
		}
	}

	public static class Sub extends RqlBiOperQuery {
		public Sub(Object ...args) {
			construct(args);
		}
		@Override
		public TermType tt() {
			return Term.TermType.SUB;
		}
	}

	public static class Mul extends RqlBiOperQuery {
		public Mul(Object ...args) {
			construct(args);
		}
		@Override
		public TermType tt() {
			return Term.TermType.MUL ;
		}
	}

	public static class Div extends RqlBiOperQuery {
		public Div(Object ...args) {
			construct(args);
		}
		@Override
		public TermType tt() {
			return Term.TermType.DIV ;
		}
	}

	public static class Mod extends RqlBiOperQuery {
		public Mod(Object ...args) {
			construct(args);
		}
		@Override
		public TermType tt() {
			return Term.TermType.MOD ;
		}
	}

	public static class Any extends RqlBiOperQuery {
		public Any(Object ...args) {
			construct(args);
		}
		@Override
		public TermType tt() {
			return Term.TermType.ANY ;
		}
	}

	public static class All extends RqlBiOperQuery {
		public All(Object ...args) {
			construct(args);
		}
		@Override
		public TermType tt() {
			return Term.TermType.ALL ;
		}
	}

}
