package uk.co.odinconsultants.memory;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class MemoryMapMain {

    /**
     * Note that there are no kernel calls when reading the file (ie, in the while loop)
     * See the kerrnel calls with:
     * <code>
     * sudo strace -p $(jstack $(jps | grep MemoryMapMain | awk '{print $1}')  | grep ^\"main | perl -pe s/\].*\//g | perl -pe s/.*\\[//g)
     * </code>
     * (Remove that second backslash in the first Perl call. I had to put it in so JavaDoc didn't complain)
     */
    public static void main(String[] args) throws Exception {
        String filename = args[0];
        System.out.println("About to read file " + filename + ". Press a key");
        System.in.read();
        Path file = Path.of(filename);
        FileChannel channel = FileChannel.open(file, StandardOpenOption.READ);
        long channelSize = channel.size();
        System.out.println("channelSize = " + channelSize);
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channelSize);
        // Read from memory mapped file buffer
        byte[] bytes = new byte[1024];
        int i = 0;
        System.out.println("Channel established. About to read file. Press any key to continue.");
        System.in.read();
        while (true) {
            if (i + bytes.length > channelSize) {
                i = 0;
                buffer.flip();
//                System.out.print("#");
            }
//            System.out.print(".");
            buffer.get(bytes, 0, bytes.length);
            i += bytes.length;
        }
    }
}
