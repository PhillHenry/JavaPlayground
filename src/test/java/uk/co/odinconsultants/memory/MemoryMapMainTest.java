package uk.co.odinconsultants.memory;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class MemoryMapMainTest {

    @Test
    public void readFromChannel() {
        var app = new MemoryMapMain();
        int bufferSize = 32;
        int maxIndex = bufferSize * 5;
        var bytes = new byte[bufferSize];
        ByteBuffer buffer = cyclicMonotonicallyIncreasing(maxIndex);
        int newIndex = app.bufferIntoArray(maxIndex, buffer, bytes, 0);
        assertEquals(bufferSize, newIndex);
        checkBytes(bytes, 0);
        int finalIndex = app.bufferIntoArray(maxIndex, buffer, bytes, newIndex);
        checkBytes(bytes, newIndex);
        assertEquals(bufferSize * 2, finalIndex);
    }

    private void checkBytes(byte[] bytes, int start) {
        byte b = (byte) (start % 256);
        for (int i = 0; i < bytes.length; i++) {
            assertEquals(b, bytes[i]);
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
