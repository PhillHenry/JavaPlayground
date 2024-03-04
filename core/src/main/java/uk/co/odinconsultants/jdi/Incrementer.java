package uk.co.odinconsultants.jdi;

import java.util.concurrent.atomic.AtomicInteger;

public class Incrementer implements Runnable {
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static int[] DO_NOT_OPTIMIZE = new int[100];
    @Override
    public void run() {
        while (counter.get() < 1e5) {
            int newVal = counter.incrementAndGet();
            DO_NOT_OPTIMIZE[newVal % DO_NOT_OPTIMIZE.length] = newVal;
        }
    }

    public static void main(String[] args) {
        Runnable runnable = new Incrementer();

        for (int i = 0 ; i < 4 ; i++) {
            new Thread(runnable).start();
        }

        System.out.println("Finished");
    }
}
