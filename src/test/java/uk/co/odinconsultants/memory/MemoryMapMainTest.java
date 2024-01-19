package uk.co.odinconsultants.memory;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

public class MemoryMapMainTest {

    private final MemoryMapMain toTest =  new MemoryMapMain();

    @Test
    public void readFromChannel() {
        int bufferSize = 32;
        int maxIndex = bufferSize * 5;
        var bytes = new byte[bufferSize];
        ByteBuffer buffer = cyclicMonotonicallyIncreasing(maxIndex);
        int newIndex = toTest.bufferIntoArray(maxIndex, buffer, bytes, 0);
        assertEquals(bufferSize, newIndex);
        checkBytes(bytes, 0);
        int finalIndex = toTest.bufferIntoArray(maxIndex, buffer, bytes, newIndex);
        checkBytes(bytes, newIndex);
        assertEquals(bufferSize * 2, finalIndex);
    }

    @Test
    public void wrapReading() {
        int bufferSize = 32;
        int maxIndex = (bufferSize * 5);
        var bytes = new byte[bufferSize];
        ByteBuffer buffer = cyclicMonotonicallyIncreasing(maxIndex);
        int start = 0;
        for (int i = 0 ; i < maxIndex ; i += bufferSize) {
            int newIndex = toTest.bufferIntoArray(maxIndex, buffer, bytes, start);
            System.out.println(String.format("%d\t%d", start, newIndex));
            checkBytes(bytes, start);
            start = newIndex;
        }
    }

    private void checkBytes(byte[] bytes, int start) {
        byte b = (byte) (start % 256);
        for (int i = 0; i < bytes.length; i++) {
            MatcherAssert.assertThat("index = " + i, bytes[i], is(equalTo(b)));
            b += 1;
        }
    }

    private ByteBuffer cyclicMonotonicallyIncreasing(int n) {
        ByteBuffer buffer = ByteBuffer.allocate(n);
        byte b = 0;
        for (int i = 0; i < n; i++) {
            buffer.put(i, b);
            b += 1;
        }
        return buffer;
    }

}
