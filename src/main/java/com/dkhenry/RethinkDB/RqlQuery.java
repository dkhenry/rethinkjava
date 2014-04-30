package com.dkhenry.RethinkDB;

import java.lang.reflect.Constructor;
import java.util.*;
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

    protected void construct_with_optargs(Object[] args, int args_required) {
        HashMap<String,Object> opt = null;
        for(int i =0; i < args.length; i++) {
            if(args[i] instanceof RqlQuery ) {
                args_required++;
            }
        }
        if( (args.length > args_required) && (args[args.length-1] instanceof HashMap)) {
            opt = (HashMap<String,Object>)args[args.length -1] ;
            args = Arrays.copyOfRange(args, 0, args.length - 1);
        }
        construct(args);
        if( null != opt ) {
            optargs(opt);
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

    protected <T extends RqlQuery> T prepend_construct_with_optargs(Object[] args,Class<T> clazz,int args_required) {
        HashMap<String,Object> optargs = null;
        if( args.length > args_required && args[args.length-1] instanceof HashMap) {
            optargs = (HashMap<String,Object>)args[args.length -1] ;
            args = Arrays.copyOfRange(args, 0, args.length - 1);
        }
        try {
            Constructor<T> ctor = clazz.getDeclaredConstructor(Object[].class);
            Object[] o = new Object[args.length+1];
            o[0] = this;
            System.arraycopy(args,0,o,1,args.length);
            T rvalue =  (T)ctor.newInstance(new Object[] { o } );
            if( null != optargs ) {
                rvalue.optargs(optargs);
            }
            return rvalue;
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

	// Stand in for default ( default is a reserved keyword )
	public RqlQuery.Default def(Object ...args) {
		return prepend_construct(args,RqlQuery.Default.class);
	}

	// Stand in for do ( do is a reserved keyword )
	public RqlQuery.FunCall call(Object ...args) {
		return prepend_construct(args,RqlQuery.FunCall.class);
	}

	public RqlMethodQuery.Update update(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Update.class);
	}

	public RqlMethodQuery.Replace replace(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Replace.class);
	}

	public RqlMethodQuery.Delete delete(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Delete.class);
	}

	public RqlMethodQuery.CoerceTo coerce_to(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.CoerceTo.class);
	}

	public RqlMethodQuery.TypeOf type_of(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.TypeOf.class);
	}

	public RqlMethodQuery.Merge merge(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Merge.class);
	}

	public RqlMethodQuery.Append append(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Append.class);
	}

	public RqlMethodQuery.Prepend prepend(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Prepend.class);
	}

	public RqlMethodQuery.Difference difference(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Difference.class);
	}

	public RqlMethodQuery.SetInsert set_insert(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.SetInsert.class);
	}

	public RqlMethodQuery.SetUnion set_union(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.SetUnion.class);
	}

	public RqlMethodQuery.SetIntersection set_intersection(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.SetIntersection.class);
	}

	public RqlMethodQuery.SetDifference set_difference(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.SetDifference.class);
	}

	public RqlQuery.Nth nth(Object ...args) {
		return prepend_construct(args,RqlQuery.Nth.class);
	}

	public RqlQuery.Match match(Object ...args) {
		return prepend_construct(args,RqlQuery.Match.class);
	}

	public RqlMethodQuery.IsEmpty is_empty(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.IsEmpty.class);
	}

	public RqlQuery.Slice slice(Object ...args) {
		return prepend_construct(args,RqlQuery.Slice.class);
	}

	public RqlMethodQuery.Skip skip(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Skip.class);
	}

	public RqlMethodQuery.Limit limit(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Limit.class);
	}

	public RqlMethodQuery.OrderBy order_by(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.OrderBy.class);
	}

	public RqlMethodQuery.Distinct distinct(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Distinct.class);
	}

	public RqlMethodQuery.Union union(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Union.class);
	}

	public RqlMethodQuery.InnerJoin inner_join(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.InnerJoin.class);
	}

	public RqlMethodQuery.OuterJoin outer_join(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.OuterJoin.class);
	}

	public RqlMethodQuery.Zip zip(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Zip.class);
	}

	public RqlMethodQuery.Info info(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Info.class);
	}

	public RqlMethodQuery.InsertAt insert_at(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.InsertAt.class);
	}

	public RqlMethodQuery.SpliceAt splice_at(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.SpliceAt.class);
	}

	public RqlMethodQuery.DeleteAt delete_at(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.DeleteAt.class);
	}

	public RqlMethodQuery.ChangeAt change_at(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.ChangeAt.class);
	}

	public RqlMethodQuery.Sample sample(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Sample.class);
	}

	/*
	 * Note: The following are supposed to be able to use functions
	 * I don't know exactly how to handle this and just want to get 
	 * some of this functionality in place before I make it all perty
	 */

	public RqlMethodQuery.IndexesOf indexes_of(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.IndexesOf.class);
	}

	public RqlMethodQuery.Reduce reduce(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Reduce.class);
	}

	public RqlMethodQuery.Map map(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Map.class);
	}

	public RqlMethodQuery.Filter filter(Object ...args) {
		return prepend_construct(args, RqlMethodQuery.Filter.class);
	}

	public RqlMethodQuery.ConcatMap concat_map(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.ConcatMap.class);
	}

	public RqlMethodQuery.Between between(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.Between.class);
	}

	public RqlMethodQuery.EqJoin eq_join(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.EqJoin.class);
	}

	public RqlMethodQuery.ForEach for_each(Object ...args) {
		return prepend_construct(args,RqlMethodQuery.ForEach.class);
	}

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
        if(t instanceof Predicate){
            return ((Predicate)t).apply();
        }
		return new RqlQuery.Datum(t);
	}

	public Term build() {
		Term.Builder t = Term.newBuilder()
				.setType(tt());
		for(RqlQuery q: _args) {
			t.addArgs(q.build());
		}
        if(! _optargs.isEmpty()) {
            for(Entry<String,Object> e: _optargs.entrySet()) {
                t.addOptargs(
                        Term.AssocPair.newBuilder()
                                .setKey(e.getKey())
                                .setVal(eval(e.getValue()).build())
                                .build()
                );
            }
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
			construct_with_optargs(args,1);
		}
		@Override
		protected TermType tt() {
			return Term.TermType.TABLE;
		}

		public RqlMethodQuery.Insert insert(Object... args) {
			return prepend_construct(args, RqlMethodQuery.Insert.class);
		}

		public RqlMethodQuery.Get get(Object ...args) {
			return prepend_construct(args, RqlMethodQuery.Get.class);
		}

		public RqlMethodQuery.GetAll get_all(Object ...args) {
			return prepend_construct(args, RqlMethodQuery.GetAll.class);
		}

		public RqlMethodQuery.IndexCreate index_create(Object ...args) {
			return prepend_construct(args, RqlMethodQuery.IndexCreate.class);
		}

		public RqlMethodQuery.IndexDrop index_drop(Object ...args) {
			return prepend_construct(args, RqlMethodQuery.IndexDrop.class);
		}

		public RqlMethodQuery.IndexList index_list(Object ...args) {
			return prepend_construct(args, RqlMethodQuery.IndexList.class);
		}

		@Override
		public RqlMethodQuery.Filter filter(Object ...args) {
			return prepend_construct(args, RqlMethodQuery.Filter.class);
		}

		public RqlMethodQuery.Count count(Object ...args) {
			return prepend_construct(args,RqlMethodQuery.Count.class);
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
