package uk.co.odinconsultants.graal;

public class NativeMain {

    public static void printAndSleep(String msg, long sleepMs) {
        try {
            System.out.println("Thread 1");
            Thread.currentThread().sleep(sleepMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (true) {
                printAndSleep("thread 1", 1000);
            }
        });
        Thread thread2 = new Thread(() -> {
            while (true) {
                printAndSleep("thread 2", 2000);
            }
        });
        thread1.start();
        thread2.start();
    }

}
