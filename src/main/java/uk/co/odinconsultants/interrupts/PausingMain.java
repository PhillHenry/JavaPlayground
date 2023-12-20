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

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String arguments = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                    String command = args[0];
                    System.out.println(String.format("About to run '%s %s'", command, arguments));
                    try {
                        var process = builder.command(command, arguments).start();
                        System.out.println(String.format("Command '%s' finished with exit code %d", command, process.exitValue()));
                    } catch(Exception x) {
                        System.err.println(String.format("Failed to execute %s", command));
                        x.printStackTrace();
                    }
                }
            }).start();
        }

        try {
            Thread.sleep(Long.MAX_VALUE); // kill -9 and -7 leads to no shutdown hook but SIGHUP does
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

}
