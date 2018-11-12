package com.example;

public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
