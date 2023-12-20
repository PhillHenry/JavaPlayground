package uk.co.odinconsultants.validation;

import jakarta.validation.constraints.NotNull; // seems javax -> jakarta
record SomeData(@NotNull(message = "Data not be null") String data, int anInt) {}

public class Records {

    public static void main(String[] args) {
        System.out.println(new SomeData("some data", 42));
    }

}
