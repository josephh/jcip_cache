package com.example;

import java.math.BigDecimal;

public class ExpensiveFunction implements Computable<String,BigDecimal> {


    @Override
    public BigDecimal compute(String arg) throws InterruptedException {
        return factorial(arg);
    }

    private BigDecimal factorial(String arg) {
        try {
            int i = Integer.parseInt(arg);
            try {
                return new BigDecimal(f(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
                return new BigDecimal(0);
            }
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException("Not an integer!");
        }
    }

    private int f(int i) throws InterruptedException {
        Thread.sleep(500);
        if(i >= 1) return i * f(--i);
        else return 1;
    }
}
