package uk.co.odinconsultants.foreign;

import java.io.IOException;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class NewUnsafeMain {

    public static void main(String[] args) throws IOException {
        waitForKeyPress();
        MemorySegment memorySegment = Arena.global().allocate(1024 * 1024 * 128, 8);
        long address = memorySegment.address();
        System.out.println(address);
        waitForKeyPress();
    }

    private static void waitForKeyPress() throws IOException {
        System.out.println("Press a key");
        System.in.read();
    }

}
