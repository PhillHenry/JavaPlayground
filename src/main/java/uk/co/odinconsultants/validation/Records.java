package uk.co.odinconsultants.validation;

import jakarta.validation.constraints.NotNull;
record SomeData(@NotNull(message = "Data not be null") String data) {}

public class Records {

    public static void main() {
        System.out.println("Hello, world");
    }

}
