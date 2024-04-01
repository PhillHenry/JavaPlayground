package uk.co.odinconsultants.memory;

public class PathologicalMemoryMain {

    public static final int BUFFER_SIZE = 1024 * 1024 * 1024;

    public static final int NUM_ITERATIONS = 100000;

    public static final int NUM_SLOTS = 14;

    public static void main(String[] args) throws Exception {
        int slot_indx = 0;
        var slots = new Object[NUM_SLOTS];
        for (int i = 0 ; i < NUM_ITERATIONS ; i++) {
            if (i % 100 == 0) System.out.print(".");
            if (i % 10000 == 0) System.out.print("");
            var buffer = new byte[BUFFER_SIZE];
            slots[slot_indx % NUM_SLOTS] = buffer;
            slot_indx++;
        }
    }
}
