package uk.co.odinconsultants.functions;

import java.util.function.Consumer;

public class FunctionsMain {

    public String myMethod(String x) {
        System.out.println("myMethod: " + x);
        return x + "x";
    }

    public void doFunction(Consumer<String> fn) {
        fn.accept("test");
    }

    public static void main(String[] args) {
        var app = new FunctionsMain();
        app.doFunction(app::myMethod);
    }

}
