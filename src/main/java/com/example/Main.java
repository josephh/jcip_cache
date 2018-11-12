package com.example;

public class Main {

    public static void main(String args[]){
        String s = "6";
        ExpensiveFunction ef = new ExpensiveFunction();
        Memoizer1 m1 = new Memoizer1(ef);
        try {
            long t = System.currentTimeMillis();
            System.out.println("??????? Factorial of " + s + " = " + m1.compute(s));
            System.out.println("First elapsed time = " + (System.currentTimeMillis() - t));
            t = System.currentTimeMillis();
            System.out.println("??????? Second factorial calculation " + s + " = " + m1.compute(s));
            System.out.println("(cached) elapsed time = " + (System.currentTimeMillis() - t));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
