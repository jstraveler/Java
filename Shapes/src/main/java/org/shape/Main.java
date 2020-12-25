package org.shape;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Shape> shapes = new ArrayList<>();
        RandomFactory factory = new RandomFactory(new ShapeFactory[]{
                new CircleFactory(),
                new TriangleFactory(),
                new RectangleFactory()
        });

        double min;
        double max = 0;
        Shape minShape = new Circle(0);
        Shape maxShape = new Circle(0);

        for(int i = 0; i < 10; i++) {
            shapes.add(factory.create());
        }

        min = shapes.get(0).getPerimeter();
        for(Shape sh : shapes) {
            if(sh.getPerimeter() < min) {
                min = sh.getPerimeter();
                minShape = sh;
            }
            if(sh.getPerimeter() > max) {
                max = sh.getPerimeter();
                maxShape = sh;
            }
        }

        System.out.printf("Smallest figure is %s" + " with perimeter %f\n", minShape.getClass().getSimpleName(), min);
        System.out.printf("Biggest figure is %s" + " with perimeter %f\n", maxShape.getClass().getSimpleName(), max);

    }
}
