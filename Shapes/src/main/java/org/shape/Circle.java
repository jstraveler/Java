package org.shape;

public class Circle implements Shape {
    private int radius;
    private double perimeter;

    Circle(int radius) {
        this.radius = radius;
    }

    @Override
    public double getPerimeter() {
        return perimeter = (2 * Math.PI * radius);
    }
}

class CircleFactory implements ShapeFactory {

    @Override
    public Shape create() {
        return new Circle(randomWithRange(1, 10));
    }

    @Override
    public int randomWithRange(int min, int max) {
        int range = Math.abs(max - min) + 1;
        return (int)(Math.random() * range) + (Math.min(min, max));
    }

}
