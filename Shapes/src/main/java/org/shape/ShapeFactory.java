package org.shape;

import java.util.Random;

public interface ShapeFactory {
    Shape create();
    int randomWithRange(int min, int max);
}

class RandomFactory implements ShapeFactory {
    ShapeFactory[] factories;
    Random r = new Random();

    public RandomFactory(ShapeFactory[] factories) {
        this.factories = factories;
    }

    @Override
    public Shape create() {
        int ind = r.nextInt(factories.length);
        return factories[ind].create();
    }

    @Override
    public int randomWithRange(int min, int max) {
        return 0;
    }


}
