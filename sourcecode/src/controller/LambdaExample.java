package controller;

public class LambdaExample {
    interface MyFunction {
        void operation();
    }

    public static void main(String[] args) {
        MyFunction printHello = () -> {
            System.out.println("Hello");
        };
        printHello.operation();
    }
}
