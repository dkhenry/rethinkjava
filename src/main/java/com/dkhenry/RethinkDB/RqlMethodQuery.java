package com.dkhenry.RethinkDB;

import com.rethinkdb.Ql2.Term.TermType;
import com.rethinkdb.Ql2.Term;

public abstract class RqlMethodQuery extends RqlQuery {
	public static class Insert extends RqlMethodQuery {
		public Insert(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.INSERT;
		}
	}
	public static class TableCreate extends RqlMethodQuery {
		public TableCreate(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.TABLE_CREATE;
		}
	}
	public static class TableDrop extends RqlMethodQuery {
		public TableDrop(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.TABLE_DROP;
		}
	}
	public static class TableList extends RqlMethodQuery {
		public TableList(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.TABLE_LIST;
		}
	}
	public static class Append extends RqlMethodQuery {
		public Append(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.APPEND;
		}
	}

	public static class Prepend extends RqlMethodQuery {
		public Prepend(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.PREPEND;
		}
	}

	public static class Difference extends RqlMethodQuery { 
		public  Difference(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.DIFFERENCE;
		}
	}

	public static class SetInsert extends RqlMethodQuery {
		public SetInsert(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.SET_INSERT;
		}
	}

	public static class SetUnion extends RqlMethodQuery {
		public SetUnion(Object ...args) {
			construct(args);		
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.SET_UNION;
		}
	}

	public static class SetIntersection extends RqlMethodQuery {
		public SetIntersection(Object ...args) { 
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.SET_INTERSECTION;
		}
	}
	public static class SetDifference extends RqlMethodQuery {
		public SetDifference(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.SET_DIFFERENCE;
		}
	}

	public static class Skip extends RqlMethodQuery {
		public Skip(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.SKIP;
		}
	}

	public static class Limit extends RqlMethodQuery {
		public Limit(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.LIMIT;
		}
	}

	public static class Contains extends RqlMethodQuery {
		public Contains(Object ...args) { 
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.CONTAINS;
		}
	}

	public static class HasFields extends RqlMethodQuery { 
		public HasFields(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.HAS_FIELDS;
		}
	}

	public static class WithFields extends RqlMethodQuery {
		public WithFields(Object ...args) {
			construct(args); 
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.WITH_FIELDS;
		}
	}

	public static class Keys extends RqlMethodQuery { 
		public Keys(Object ...args) { 
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.KEYS;
		}
	}

	public static class Pluck extends RqlMethodQuery {
		public Pluck(Object ...args) { 
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.PLUCK;
		}
	}
	public static class Without extends RqlMethodQuery {
		public  Without(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.WITHOUT;
		}
	}

	public static class Merge extends RqlMethodQuery {
		public  Merge(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.MERGE;
		}
	}

	public static class Between extends RqlMethodQuery {
		public  Between(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.BETWEEN;
		}
	}

	public static class Get extends RqlMethodQuery {
		public  Get(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.GET;
		}
	}

	public static class GetAll extends RqlMethodQuery {
		public  GetAll(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.GET_ALL;
		}
	}

	public static class Reduce extends RqlMethodQuery {
		public  Reduce(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.REDUCE;
		}
	}

	public static class Map extends RqlMethodQuery {
		public  Map(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.MAP;
		}
	}

	public static class Filter extends RqlMethodQuery {
		public  Filter(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.FILTER;
		}
	}

	public static class ConcatMap extends RqlMethodQuery {
		public  ConcatMap(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.CONCATMAP;
		}
	}

	public static class OrderBy extends RqlMethodQuery {
		public  OrderBy(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.ORDERBY;
		}
	}

	public static class Distinct extends RqlMethodQuery {
		public  Distinct(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.DISTINCT;
		}
	}

	public static class Count extends RqlMethodQuery {
		public  Count(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.COUNT;
		}
	}

	public static class Union extends RqlMethodQuery {
		public  Union(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.UNION;
		}
	}

	public static class IndexesOf extends RqlMethodQuery {
		public  IndexesOf(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.INDEXES_OF;
		}
	}

	public static class IsEmpty extends RqlMethodQuery {
		public  IsEmpty(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.IS_EMPTY;
		}
	}

	public static class GroupedMapReduce extends RqlMethodQuery {
		public  GroupedMapReduce(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.GROUPED_MAP_REDUCE;
		}
	}

	public static class GroupBy extends RqlMethodQuery {
		public  GroupBy(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.GROUPBY;
		}
	}

	public static class InnerJoin extends RqlMethodQuery {
		public  InnerJoin(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.INNER_JOIN;
		}
	}

	public static class OuterJoin extends RqlMethodQuery {
		public  OuterJoin(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.OUTER_JOIN;
		}
	}

	public static class EqJoin extends RqlMethodQuery {
		public  EqJoin(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.EQ_JOIN;
		}
	}

	public static class Zip extends RqlMethodQuery {
		public  Zip(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.ZIP;
		}
	}

	public static class CoerceTo extends RqlMethodQuery {
		public  CoerceTo(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.COERCE_TO;
		}
	}

	public static class TypeOf extends RqlMethodQuery {
		public  TypeOf(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.TYPEOF;
		}
	}

	public static class Update extends RqlMethodQuery {
		public  Update(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.UPDATE;
		}
	}

	public static class Delete extends RqlMethodQuery {
		public  Delete(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.DELETE;
		}
	}

	public static class Replace extends RqlMethodQuery {
		public  Replace(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.REPLACE;
		}
	}

	public static class IndexCreate extends RqlMethodQuery {
		public  IndexCreate(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.INDEX_CREATE;
		}
	}

	public static class IndexDrop extends RqlMethodQuery {
		public  IndexDrop(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.INDEX_DROP;
		}
	}

	public static class IndexList extends RqlMethodQuery {
		public  IndexList(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.INDEX_LIST;
		}
	}

	public static class ForEach extends RqlMethodQuery {
		public  ForEach(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.FOREACH;
		}
	}

	public static class Info extends RqlMethodQuery {
		public  Info(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.INFO;
		}
	}

	public static class InsertAt extends RqlMethodQuery {
		public  InsertAt(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.INSERT_AT;
		}
	}

	public static class SpliceAt extends RqlMethodQuery {
		public  SpliceAt(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.SPLICE_AT;
		}
	}

	public static class DeleteAt extends RqlMethodQuery {
		public  DeleteAt(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.DELETE_AT;
		}
	}

	public static class ChangeAt extends RqlMethodQuery {
		public  ChangeAt(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.CHANGE_AT;
		}
	}

	public static class Sample extends RqlMethodQuery {
		public  Sample(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.SAMPLE;
		}
	}
}

