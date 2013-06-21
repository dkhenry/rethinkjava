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
/*
 * 
class Append(RqlMethodQuery):
    tt = p.Term.APPEND
    st = "append"

class Prepend(RqlMethodQuery):
    tt = p.Term.PREPEND
    st = "prepend"

class Difference(RqlMethodQuery):
    tt = p.Term.DIFFERENCE
    st = "difference"

class SetInsert(RqlMethodQuery):
    tt = p.Term.SET_INSERT
    st = "set_insert"

class SetUnion(RqlMethodQuery):
    tt = p.Term.SET_UNION
    st = "set_union"

class SetIntersection(RqlMethodQuery):
    tt = p.Term.SET_INTERSECTION
    st = "set_intersection"

class SetDifference(RqlMethodQuery):
    tt = p.Term.SET_DIFFERENCE
    st = "set_difference"

class Skip(RqlMethodQuery):
    tt = p.Term.SKIP
    st = 'skip'

class Limit(RqlMethodQuery):
    tt = p.Term.LIMIT
    st = 'limit'
class Contains(RqlMethodQuery):
    tt = p.Term.CONTAINS
    st = 'contains'

class HasFields(RqlMethodQuery):
    tt = p.Term.HAS_FIELDS
    st = 'has_fields'

class WithFields(RqlMethodQuery):
    tt = p.Term.WITH_FIELDS
    st = 'with_fields'

class Keys(RqlMethodQuery):
    tt = p.Term.KEYS
    st = 'keys'

class Pluck(RqlMethodQuery):
    tt = p.Term.PLUCK
    st = 'pluck'

class Without(RqlMethodQuery):
    tt = p.Term.WITHOUT
    st = 'without'

class Merge(RqlMethodQuery):
    tt = p.Term.MERGE
    st = 'merge'

class Between(RqlMethodQuery):
    tt = p.Term.BETWEEN
    st = 'between'

class Get(RqlMethodQuery):
    tt = p.Term.GET
    st = 'get'

class GetAll(RqlMethodQuery):
    tt = p.Term.GET_ALL
    st = 'get_all'

class Reduce(RqlMethodQuery):
    tt = p.Term.REDUCE
    st = 'reduce'

class Map(RqlMethodQuery):
    tt = p.Term.MAP
    st = 'map'

class Filter(RqlMethodQuery):
    tt = p.Term.FILTER
    st = 'filter'

class ConcatMap(RqlMethodQuery):
    tt = p.Term.CONCATMAP
    st = 'concat_map'

class OrderBy(RqlMethodQuery):
    tt = p.Term.ORDERBY
    st = 'order_by'

class Distinct(RqlMethodQuery):
    tt = p.Term.DISTINCT
    st = 'distinct'

class Count(RqlMethodQuery):
    tt = p.Term.COUNT
    st = 'count'

class Union(RqlMethodQuery):
    tt = p.Term.UNION
    st = 'union'
class IndexesOf(RqlMethodQuery):
    tt = p.Term.INDEXES_OF
    st = 'indexes_of'

class IsEmpty(RqlMethodQuery):
    tt = p.Term.IS_EMPTY
    st = 'is_empty'

class IndexesOf(RqlMethodQuery):
    tt = p.Term.INDEXES_OF
    st = 'indexes_of'

class GroupedMapReduce(RqlMethodQuery):
    tt = p.Term.GROUPED_MAP_REDUCE
    st = 'grouped_map_reduce'

class GroupBy(RqlMethodQuery):
    tt = p.Term.GROUPBY
    st = 'group_by'

class InnerJoin(RqlMethodQuery):
    tt = p.Term.INNER_JOIN
    st = 'inner_join'

class OuterJoin(RqlMethodQuery):
    tt = p.Term.OUTER_JOIN
    st = 'outer_join'

class EqJoin(RqlMethodQuery):
    tt = p.Term.EQ_JOIN
    st = 'eq_join'

class Zip(RqlMethodQuery):
    tt = p.Term.ZIP
    st = 'zip'

class CoerceTo(RqlMethodQuery):
    tt = p.Term.COERCE_TO
    st = 'coerce_to'

class TypeOf(RqlMethodQuery):
    tt = p.Term.TYPEOF
    st = 'type_of'

class Update(RqlMethodQuery):
    tt = p.Term.UPDATE
    st = 'update'

class Delete(RqlMethodQuery):
    tt = p.Term.DELETE
    st = 'delete'

class Replace(RqlMethodQuery):
    tt = p.Term.REPLACE
    st = 'replace'

class Insert(RqlMethodQuery):
    tt = p.Term.INSERT
    st = 'insert'
    
class TableCreate(RqlMethodQuery):
    tt = p.Term.TABLE_CREATE
    st = "table_create"

class TableDrop(RqlMethodQuery):
    tt = p.Term.TABLE_DROP
    st = "table_drop"

class TableList(RqlMethodQuery):
    tt = p.Term.TABLE_LIST
    st = "table_list"

class IndexCreate(RqlMethodQuery):
    tt = p.Term.INDEX_CREATE
    st = 'index_create'

class IndexDrop(RqlMethodQuery):
    tt = p.Term.INDEX_DROP
    st = 'index_drop'

class IndexList(RqlMethodQuery):
    tt = p.Term.INDEX_LIST
    st = 'index_list'

class ForEach(RqlMethodQuery):
    tt = p.Term.FOREACH
    st = 'for_each'
class Info(RqlMethodQuery):
    tt = p.Term.INFO
    st = 'info'

class InsertAt(RqlMethodQuery):
    tt = p.Term.INSERT_AT
    st = 'insert_at'

class SpliceAt(RqlMethodQuery):
    tt = p.Term.SPLICE_AT
    st = 'splice_at'

class DeleteAt(RqlMethodQuery):
    tt = p.Term.DELETE_AT
    st = 'delete_at'

class ChangeAt(RqlMethodQuery):
    tt = p.Term.CHANGE_AT
    st = 'change_at'

class Sample(RqlMethodQuery):
    tt = p.Term.SAMPLE
    st = 'sample'


 */
}
