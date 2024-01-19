package uk.co.odinconsultants.memory;

import org.junit.Assert;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.nio.ByteBuffer;

public class MemoryMapMainTest {

    @Test
    public void readFromChannel() {
        var app = new MemoryMapMain();
        int bufferSize = 1024;
        int n = bufferSize * 5;
        var bytes = new byte[bufferSize];
        ByteBuffer buffer = ByteBuffer.allocate(n);
        int newIndex = app.bufferIntoArray(n, buffer, bytes, 0);
        Assert.assertEquals(bufferSize, newIndex);
    }

}
