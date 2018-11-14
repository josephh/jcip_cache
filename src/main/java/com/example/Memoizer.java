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
        while (true) {
            A a = (A) arg;
            Future<V> f = cache.get(a);
            if (f == null) {
                FutureTask<V> ft = new FutureTask<>(() -> {
                    System.out.println(a + " factorial not cached...computing");
                    return c.compute(a);
                });
                f = cache.putIfAbsent(a, ft); // atomic!
                if (f == null) {   /** ConcurrentHashMap.putIfAbsent(x, y)
                                    * returns 'the previous value associated with the
                                    * specified key, or null if there was no mapping
                                    * for the key.'
                                    */
                    f = ft; // the future is now the FutureTask just registered in the cache
                    ft.run();
                }
            }
            try {
                return f.get();  // blocks until done...
            } catch (CancellationException ce) {
                cache.remove(a); // unregister a failed task to avoid cache 'pollution'
            } catch (ExecutionException e) {
                launderException(e.getCause());
            }
        }
    }

    private InterruptedException launderException(Throwable cause) {
        throw new RuntimeException("Error: " + cause);
    }

}
