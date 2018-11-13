package com.example;

public class Main {

    static Memoizer1 m1 = null ;

    public static void main(String args[]) {
        long t = System.currentTimeMillis();
        String s = "6";
        ExpensiveFunction ef = new ExpensiveFunction();
        m1 = new Memoizer1(ef);
        ClientThreads ct = new ClientThreads("3");
        ClientThreads ct1 = new ClientThreads("4");
        ClientThreads ct2 = new ClientThreads("3");
        ct.run();
        ct1.run();
        ct2.run();
        System.out.println(">>> Total running time : " + (System.currentTimeMillis() - t));
    }

    private static class ClientThreads implements Runnable {
        private final String s;

        public ClientThreads(String s) {
            this.s = s;
        }

        @Override
        public void run() {
            try {
                long t = System.currentTimeMillis();
                System.out.println("??????? Factorial of " + s + " = " + m1.compute(s));
                System.out.println("::: compute(...) elapsed running time : " + (System.currentTimeMillis() - t));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



