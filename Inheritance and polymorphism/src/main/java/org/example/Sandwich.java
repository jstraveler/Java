package org.example;

public class Sandwich implements Food {
    public Sandwich() {
    }

    public void price() {
        System.out.println("It was cost not a lot, big sandwich");
    }

    public void print() {
        System.out.println(this.getClass().getSimpleName());
    }

    public void eat() {
        System.out.println("Oh no, I was eaten");
    }
}