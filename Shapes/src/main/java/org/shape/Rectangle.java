package org.shape;

public class Rectangle implements Shape {
    private int sideA;
    private int sideB;
    private double perimeter;

    Rectangle(int sideA, int sideB) {
        this.sideA = sideA;
        this.sideB = sideB;
    }

    @Override
    public double getPerimeter() {
        return perimeter = ((sideA + sideB) * 2);
    }
}

class RectangleFactory implements ShapeFactory {

    @Override
    public Shape create() {
        return new Rectangle(randomWithRange(1, 10), randomWithRange(1, 10));
    }

    @Override
    public int randomWithRange(int min, int max) {
        int range = Math.abs(max - min) + 1;
        return (int)(Math.random() * range) + (Math.min(min, max));
    }
}
