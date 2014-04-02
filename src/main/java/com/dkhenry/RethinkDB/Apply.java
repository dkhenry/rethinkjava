package com.dkhenry.RethinkDB;

interface Apply<T, R> {
    public R apply(T var);
}
