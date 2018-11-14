package com.example;

import net.jcip.annotations.GuardedBy;

import java.util.Map;
import java.util.concurrent.*;

public class Memoizer<A, V> implements Computable {

    @GuardedBy("this")
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(Object arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        A a = (A) arg;
        if(f == null) {
//            Callable<V> eval = new Callable<V>() {
//                @Override
//                public V call() throws Exception {
//                    System.out.println(a +  " factorial not cached...computing");
//                    return c.compute(a);
//                }
//            }
//            FutureTask<V> ft = new FutureTask<>(eval);
            /** The above code - defining a Callable object and passing it
             * to the FutureTask constructor, can be rewritten more concisely with
             * a lambda directly in the FutureTask constructor, as follows...
             */
            FutureTask<V> ft = new FutureTask<>(() -> {
                System.out.println(a +  " factorial not cached...computing");
                return c.compute(a);
            });
            f = ft;
            cache.put(a, ft);  // the future (rather than a known result) goes in the cache
            ft.run();
        }
        try{
            return f.get();
        } catch (ExecutionException e) {
            throw launderException(e.getCause());
        }
    }

    private InterruptedException launderException(Throwable cause) {
        throw new RuntimeException("Error: " + cause);
    }

}
