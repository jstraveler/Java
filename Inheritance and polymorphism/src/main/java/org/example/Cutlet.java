package org.example;

public class Cutlet implements Food {
    public Cutlet() {
    }

    public void price() {
        System.out.println("It was cost a lot, simple cutlet");
    }

    public void print() {
        System.out.println(this.getClass().getSimpleName());
    }

    public void eat() {
        System.out.println("Oh no, I was eaten");
    }
}