package org.shape;

public class Triangle implements Shape {
    private int side;
    private double perimeter;

    Triangle(int side) {
        this.side = side;
    }

    @Override
    public double getPerimeter() {
        return perimeter = (side * 3);
    }
}

class TriangleFactory implements ShapeFactory {

    @Override
    public Shape create() {
        return new Triangle(randomWithRange(1, 10));
    }

    @Override
    public int randomWithRange(int min, int max) {
        int range = Math.abs(max - min) + 1;
        return (int)(Math.random() * range) + (Math.min(min, max));
    }
}
