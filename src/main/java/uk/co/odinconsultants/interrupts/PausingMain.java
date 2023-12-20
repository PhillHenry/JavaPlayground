package uk.co.odinconsultants.interrupts;

import java.util.Arrays;

public class PausingMain {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Shutdown hook run");
                super.run();
            }
        });

        if (args.length > 0) {
            var builder = new ProcessBuilder();
            String command = Arrays.toString(args);
            System.out.println(String.format("About to run %s", command));
            try {
                var process = builder.command(command).start();
            } catch(Exception x) {
                System.err.println(String.format("Failed to execute %s", command));
                x.printStackTrace();
            }
        }

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

}
