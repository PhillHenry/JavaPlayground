package uk.co.odinconsultants.functions;

import java.util.function.Consumer;

public class FunctionsMain {

    public String myMethod(String x) {
        System.out.println("myMethod: " + x);
        return x + "x";
    }
    public String myMethod(String x, String y) {
        System.out.println("Currying myMethod: " + x + ", " + y);
        return x + "x";
    }

    public void doFunction(Consumer<String> fn) {
        fn.accept("test");
    }

    public static void main(String[] args) {
        var app = new FunctionsMain();
        app.doFunction(app::myMethod);
        app.doFunction(b -> app.myMethod("curry", b));
    }

}
