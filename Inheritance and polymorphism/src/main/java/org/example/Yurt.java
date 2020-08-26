package org.example;

public class Yurt implements House {
    public int count;

    Yurt() {
        ++this.count;
    }

    public void price() {
    }

    public void print() {
        System.out.println(this.getClass().getSimpleName());
    }

    public void live() {
    }
}