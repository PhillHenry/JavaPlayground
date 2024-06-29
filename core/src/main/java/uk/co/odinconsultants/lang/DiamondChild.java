package uk.co.odinconsultants.lang;

public class DiamondChild implements InterfaceA /*, InterfaceB*/ {

    public static void main(String[] args) {
        var app = new DiamondChild();
        System.out.println("Cannot implement both interfaces. method returns " + app.helloWorld());
    }

}
