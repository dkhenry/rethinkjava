package com.dkhenry.RethinkDB;

import java.util.List;

abstract public class Function extends Predicate.Predicate1 implements Apply<RqlQuery.Var, RqlQuery> {


    @Override
    protected Integer getAmount() {
        return 1;
    }

    @Override
    protected RqlQuery internalApply(List<RqlQuery.Var> vars) {
        return apply(vars.get(0));
    }
}



