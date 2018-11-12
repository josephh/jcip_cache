package com.example;

import net.jcip.annotations.GuardedBy;

import java.util.HashMap;
import java.util.Map;

public class Memoizer1<A, V> implements Computable {

    @GuardedBy("this")
    private final Map<A, V> cache = new HashMap<>();
    private final Computable<A, V> c;

    public Memoizer1(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(Object arg) throws InterruptedException {
        V result = cache.get(arg);
        A a = (A) arg;
        if (result == null) {
            System.out.println(a +  " factorial not cached...computing");
            result = c.compute(a);
            cache.put(a, result);
        }
        return result;
    }

}
