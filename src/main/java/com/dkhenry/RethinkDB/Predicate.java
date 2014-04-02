package com.dkhenry.RethinkDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

abstract class Predicate {


    static abstract class Predicate1 extends Predicate {

    }

    static abstract class Predicate2 extends Predicate {

    }


    private static final AtomicInteger _nextVarId = new AtomicInteger();

    private Integer nextVarId() {
        return _nextVarId.incrementAndGet();
    }

    protected abstract Integer getAmount();


    abstract protected RqlQuery internalApply(List<RqlQuery.Var> vars);

    class InvokeResults {
        RqlQuery.MakeArray ids;
        RqlQuery product;

        InvokeResults(RqlQuery.MakeArray ids, RqlQuery product) {
            this.ids = ids;
            this.product = product;
        }
    }

    private InvokeResults invoke() {
        Integer amount = getAmount();
        final ArrayList<Integer> ids = new ArrayList<Integer>(amount);
        final ArrayList<RqlQuery.Var> vars = new ArrayList<RqlQuery.Var>(amount);
        int varId;
        for ( int i = 1; i <= amount; i++ ) {
            varId = nextVarId();
            ids.add(varId);
            vars.add(new RqlQuery.Var(varId));
        }


        return new InvokeResults(new RqlQuery.MakeArray(ids), internalApply(vars));

    }

    public RqlQuery apply() {
        InvokeResults results = invoke();
        return new RqlQuery.Func(results.ids, results.product);
    }
}

