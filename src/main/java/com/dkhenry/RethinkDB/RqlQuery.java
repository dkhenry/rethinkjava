package com.dkhenry.RethinkDB;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

	protected <T extends RqlQuery> T prepend_construct(Object[] args,Class<T> clazz) {
		try {			
			Constructor<T> ctor = clazz.getDeclaredConstructor(Object[].class);					
			Object[] o = new Object[args.length+1];
			o[0] = this;
			System.arraycopy(args,0,o,1,args.length);	
			return (T)ctor.newInstance(new Object[] { o });							
		} catch (Exception ex ) { 			
			return null;
		}
	}
	
	public RqlQuery optargs(HashMap<String,Object> args) { 
		_optargs.putAll(args);
		return this;
	}

	abstract protected Term.TermType tt() ;

	public RqlBiOperQuery.Eq eq(Object ...args) {
		return prepend_construct(args,RqlBiOperQuery.Eq.class);
	}
	public RqlBiOperQuery.Ne ne(Object ...args) {
		return prepend_construct(args,RqlBiOperQuery.Ne.class);
	}

	public RqlBiOperQuery.Lt lt(Object ...args) {
		return prepend_construct(args,RqlBiOperQuery.Lt.class);
	}

	public RqlBiOperQuery.Le le(Object ...args) {
		return prepend_construct(args,RqlBiOperQuery.Le.class);
	}

	public RqlBiOperQuery.Gt gt(Object ...args) {
		return prepend_construct(args,RqlBiOperQuery.Gt.class);
	}

	public RqlBiOperQuery.Ge ge(Object ...args) {
		return prepend_construct(args,RqlBiOperQuery.Ge.class);
	}

	public RqlBiOperQuery.Add add(Object ...args) {
		return prepend_construct(args,RqlBiOperQuery.Add.class);
	}

	public RqlBiOperQuery.Sub sub(Object ...args) {
		return prepend_construct(args,RqlBiOperQuery.Sub.class);
	}

	public RqlBiOperQuery.Mul mul(Object ...args) {
		return prepend_construct(args,RqlBiOperQuery.Mul.class);
	}

	public RqlBiOperQuery.Div div(Object ...args) {
		return prepend_construct(args,RqlBiOperQuery.Div.class);
	}
	
	public RqlBiOperQuery.Mod mod(Object ...args) {
		return prepend_construct(args,RqlBiOperQuery.Mod.class);
	}
	
	public RqlMethodQuery.Contains contains(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Contains.class);
	}
	
	public RqlMethodQuery.HasFields has_fields(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.HasFields.class);
	}
	
	public RqlMethodQuery.WithFields with_fields(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.WithFields.class);
	}
	
	public RqlMethodQuery.Keys keys(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Keys.class);
	}
	
	public RqlMethodQuery.Pluck pluck(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Pluck.class);
	}
	
	public RqlMethodQuery.Without without(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Without.class);
	}
	
	// Stand in for default ( default is a reserved keyword 
	public RqlQuery.Default def(Object ...args) {
		return prepend_construct(args,RqlQuery.Default.class);
	}
	
	// Stand in for do ( do is a reserved keyword )
	public RqlQuery.FunCall call(Object function,Object ...args) {		
		return prepend_construct(args,RqlQuery.FunCall.class);
	}

	/* Convience Methods to make a pretty API */
	/*

    # Polymorphic object/sequence operations
    def do(self, func):
        return FunCall(func_wrap(func), self)
   
    def update(self, func, non_atomic=(), durability=(), return_vals=()):
        return Update(self, func_wrap(func), non_atomic=non_atomic,
                      durability=durability, return_vals=return_vals)

    def replace(self, func, non_atomic=(), durability=(), return_vals=()):
        return Replace(self, func_wrap(func), non_atomic=non_atomic,
                       durability=durability, return_vals=return_vals)

    def delete(self, durability=(), return_vals=()):
        return Delete(self, durability=durability, return_vals=return_vals)

    # Rql type inspection
    def coerce_to(self, other_type):
        return CoerceTo(self, other_type)

    def type_of(self):
        return TypeOf(self)

    def merge(self, other):
        return Merge(self, other)

    def append(self, val):
        return Append(self, val)

    def prepend(self, val):
        return Prepend(self, val)

    def difference(self, val):
        return Difference(self, val)

    def set_insert(self, val):
        return SetInsert(self, val)

    def set_union(self, val):
        return SetUnion(self, val)

    def set_intersection(self, val):
        return SetIntersection(self, val)

    def set_difference(self, val):
        return SetDifference(self, val)

    # Operator used for get attr / nth / slice. Non-operator versions below
    # in cases of ambiguity
    def __getitem__(self, index):
        if isinstance(index, slice):
            return Slice(self, index.start or 0, index.stop or -1)
        elif isinstance(index, int):
            return Nth(self, index)
        elif isinstance(index, types.StringTypes):
            return GetField(self, index)

    def nth(self, index):
        return Nth(self, index)

    def match(self, pattern):
        return Match(self, pattern)

    def is_empty(self):
        return IsEmpty(self)

    def indexes_of(self, val):
        return IndexesOf(self,func_wrap(val))

    def slice(self, left=None, right=None):
        return Slice(self, left, right)

    def skip(self, index):
        return Skip(self, index)

    def limit(self, index):
        return Limit(self, index)

    def reduce(self, func, base=()):
        return Reduce(self, func_wrap(func), base=base)

    def map(self, func):
        return Map(self, func_wrap(func))

    def filter(self, func, default=()):
        return Filter(self, func_wrap(func), default=default)

    def concat_map(self, func):
        return ConcatMap(self, func_wrap(func))

    def order_by(self, *obs):
        return OrderBy(self, *obs)

    def between(self, left_bound=None, right_bound=None, index=()):
        return Between(self, left_bound, right_bound, index=index)

    def distinct(self):
        return Distinct(self)

    # NB: Can't overload __len__ because Python doesn't
    #     allow us to return a non-integer
    def count(self, filter=()):
        if filter == ():
            return Count(self)
        else:
            return Count(self, func_wrap(filter))

    def union(self, *others):
        return Union(self, *others)

    def inner_join(self, other, predicate):
        return InnerJoin(self, other, predicate)

    def outer_join(self, other, predicate):
        return OuterJoin(self, other, predicate)

    def eq_join(self, left_attr, other, index=()):
        return EqJoin(self, left_attr, other, index=index)

    def zip(self):
        return Zip(self)

    def grouped_map_reduce(self, grouping, mapping, data_collector, base=()):
        return GroupedMapReduce(self, func_wrap(grouping), func_wrap(mapping),
            func_wrap(data_collector), base=base)

    def group_by(self, arg1, arg2, *rest):
        args = [arg1, arg2] + list(rest)
        return GroupBy(self, list(args[:-1]), args[-1])

    def for_each(self, mapping):
        return ForEach(self, func_wrap(mapping))

    def info(self):
        return Info(self)

    # Array only operations
    def insert_at(self, index, value):
        return InsertAt(self, index, value)

    def splice_at(self, index, values):
        return SpliceAt(self, index, values)

    def delete_at(self, *indexes):
        return DeleteAt(self, *indexes);

    def change_at(self, index, value):
        return ChangeAt(self, index, value);

    def sample(self, count):
        return Sample(self, count)
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> RqlQuery eval(T t) {
		if( t instanceof RqlQuery) { 
			return (RqlQuery)t;
		}
		if (t instanceof List) {
			return new MakeArray((List)t);
		}
		if (t instanceof Map) {			 		
			return new MakeObj((Map)t);
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

	public static class MakeObj extends RqlQuery { 		
		public <V extends RqlQuery> MakeObj(Map<String,V> m) {
			super();			
			_optargs.putAll(m);			
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.MAKE_OBJ;
		}
	}

	public static class Var extends RqlQuery {
		public Var(Object ...args) {
			construct(args);
		}
		@Override
		protected TermType tt() {			
			return Term.TermType.VAR;
		}
	}

	public static class Default extends RqlQuery {
		public Default(Object ...args) {
			construct(args);
		}

		@Override
		protected TermType tt() {			
			return Term.TermType.DEFAULT;
		}
	}

	public static class ImplicitVar extends RqlQuery {
		public ImplicitVar(Object ...args) {
			construct(args);
		}

		@Override
		protected TermType tt() {			
			return Term.TermType.IMPLICIT_VAR;
		}
	}

	public static class Not extends RqlQuery {
		public Not(Object ...args) {
			construct(args);
		}

		@Override
		protected TermType tt() {			
			return Term.TermType.NOT;
		}
	}

	public static class Slice extends RqlQuery {
		public Slice(Object ...args) {
			construct(args);
		}

		@Override
		protected TermType tt() {			
			return Term.TermType.SLICE;
		}
	}

	public static class GetField extends RqlQuery {
		public GetField(Object ...args) {
			construct(args);
		}

		@Override
		protected TermType tt() {			
			return Term.TermType.GET_FIELD;
		}
	}

	public static class FunCall extends RqlQuery {
		public FunCall(Object ...args) {
			construct(args);
		}

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
		public RqlMethodQuery.Filter filter(Object ...args) { 
			Object[] o = new Object[args.length+1];
			o[0] = this;
			System.arraycopy(args,0,o,1,args.length);
			RqlMethodQuery.Filter rvalue =  new RqlMethodQuery.Filter();
			rvalue.construct(o);
			return rvalue;
		}
	}

	public static class Nth extends RqlQuery {
		public Nth(Object ...args) {
			construct(args);
		}

		@Override
		protected TermType tt() {			
			return Term.TermType.NTH;
		}
	}

	public static class Match extends RqlQuery {
		public Match(Object ...args) {
			construct(args);
		}

		@Override
		protected TermType tt() {			
			return Term.TermType.MATCH;
		}
	}	

	public static class Func extends RqlQuery {
		public Func(Object ...args) {
			construct(args);
		}

		@Override
		protected TermType tt() {			
			return Term.TermType.FUNC;
		}
	}
}
