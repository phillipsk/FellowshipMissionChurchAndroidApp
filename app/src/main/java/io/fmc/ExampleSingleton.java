package io.fmc;

public class ExampleSingleton {

    ExampleSingleton self;

    ExampleSingleton getInstance() {
        if (self == null) {
            self = new ExampleSingleton();
        }
        return self;
    }

    private ExampleSingleton() {

    }
}
