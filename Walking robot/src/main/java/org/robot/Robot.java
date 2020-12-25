package org.robot;

import java.util.concurrent.SynchronousQueue;

class Robot {
    private final static SynchronousQueue<Integer> queue = new SynchronousQueue<>();
    private final Left left;
    private final Right right;

    Robot(int steps) {
        left = new Left(queue, steps);
        right = new Right(queue, steps);
    }

    Left getLeftLeg() {
        return left;
    }

    Right getRightLeg() {
        return right;
    }
}

class Left implements Runnable {
    private final Thread t;
    private final SynchronousQueue<Integer> queue;
    private final int steps;

    Left(SynchronousQueue<Integer> queue, int steps) {
        this.queue = queue;
        this.steps = steps;
        t = new Thread(this, "Left");
        t.start();
    }

    Thread getThread() {
        return t;
    }

    @Override
    public void run() {
        int step;
        int i = 1;
        while(i <= steps) {
            try {
                step = i;
                queue.put(i); // thread will block here
                if (queue.isEmpty()) {
                    System.out.printf("%s leg step number : %s %n",
                            Thread.currentThread().getName(), step);
                }
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class Right implements Runnable {
    private final Thread t;
    private final SynchronousQueue<?> queue;
    private final int steps;

    Right(SynchronousQueue<?> queue, int steps) {
        this.queue = queue;
        this.steps = steps;
        t = new Thread(this, "Right");
        t.start();
    }

    Thread getThread() {
        return t;
    }

    @Override
    public void run() {
        int i = 1;
        while (i <= steps) {
            try {
                int step = (int) queue.take(); // thread will block here
                System.out.printf("%s leg step number: %s %n",
                        Thread.currentThread().getName(), step);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
