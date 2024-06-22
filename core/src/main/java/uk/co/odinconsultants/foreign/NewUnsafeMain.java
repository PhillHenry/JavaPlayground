package uk.co.odinconsultants.foreign;

import java.io.IOException;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class NewUnsafeMain {

    public static void main(String[] args) throws IOException {
        MemorySegment memorySegment = Arena.global().allocate(1024, 8);
        long address = memorySegment.address();
        System.out.println(address);
        System.out.println("Press a key");
        System.in.read();
    }

}
