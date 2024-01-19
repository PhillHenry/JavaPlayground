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
        int readFirst = toTest.readIntoArray(maxIndex, buffer, bytes, 0);
        assertEquals(bufferSize, readFirst);
        checkBytes(bytes, 0, readFirst);
        int readSecond = toTest.readIntoArray(maxIndex, buffer, bytes, readFirst);
        checkBytes(bytes, readFirst, readSecond);
        assertEquals(bufferSize * 2, readSecond + readFirst);
    }

    @Test
    public void edgeCase() {
        int bufferSize = 32;
        int maxIndex = (bufferSize * 5) - 1;
        var bytes = new byte[bufferSize];
        ByteBuffer buffer = cyclicMonotonicallyIncreasing(maxIndex);
        int start = 0;
        for (int i = 0 ; i < maxIndex ; i += bufferSize) {
            int read = toTest.readIntoArray(maxIndex, buffer, bytes, start);
            System.out.println(String.format("start = %d\t read = %d\tmaxIndex = %d", start, read, maxIndex));
            checkBytes(bytes, start, read);
            start += read;
        }
    }
    @Test
    public void wrapReading() {
        int bufferSize = 32;
        int maxIndex = (bufferSize * 5) - 1;
        var bytes = new byte[bufferSize];
        ByteBuffer buffer = cyclicMonotonicallyIncreasing(maxIndex);
        int i = 0;
        int nLoops = 0;
        while (nLoops < 5) {
            int old = i;
            i = toTest.readLooping(maxIndex, buffer, bytes, i);
            System.out.println(String.format("i = %d\told = %d\tnLoops = %d", i, old, nLoops));
            if (i < old) {
                nLoops += 1;
            }
        }
    }

    private void checkBytes(byte[] bytes, int start, int length) {
        byte b = (byte) (start % 256);
        for (int i = 0; i < length; i++) {
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
