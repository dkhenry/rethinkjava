package com.dkhenry.RethinkDB;

import java.util.List;

abstract public class Function2 extends Predicate.Predicate2 implements Apply2<RqlQuery.Var, RqlQuery.Var, RqlQuery> {


    @Override
    protected Integer getAmount() {
        return 2;
    }

    @Override
    protected RqlQuery internalApply(List<RqlQuery.Var> vars) {
        return apply(vars.get(0), vars.get(1));
    }
}
