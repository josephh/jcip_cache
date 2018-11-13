package com.example;

import net.jcip.annotations.GuardedBy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Memoizer<A, V> implements Computable {

    @GuardedBy("this")
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(Object arg) throws InterruptedException {
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
