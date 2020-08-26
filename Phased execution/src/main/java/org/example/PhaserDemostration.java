package org.example;

import java.util.concurrent.Phaser;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

class PhaserDemostration implements Demonstration {
    int amount_of_workers = 0;
    int amount_of_boxes = 0;
    int amount_of_phases = 1;
    boolean correct_data = false;
    SynchronousQueue<Integer> queue = new SynchronousQueue<>();
    AtomicInteger dec = new AtomicInteger(0);

    PhaserDemostration(int amount_of_workers, int amount_of_boxes) {
        this.amount_of_workers = amount_of_workers;
        this.amount_of_boxes = amount_of_boxes;
        try {
            calculate_parameters();
        } catch (ArithmeticException e) {
            System.out.println("Data was entered incorrectly");
            //e.printStackTrace();
        }
    }

    public void calculate_parameters() {
        if (amount_of_workers > 0  && amount_of_boxes > 0) { // Д.б. больше нуля
            amount_of_phases = (amount_of_boxes / amount_of_workers);
            correct_data = true;
            if (amount_of_boxes % amount_of_workers != 0) { // Если кол-во ящиков не делится на кол-во работников без остатка
                amount_of_phases++;                              // Увеличиваем кол-во барьеров на 1
            }
        } else {
            System.out.println("Amount of workers must be greater than zero");
        }
    }

    public void start_gather() {
        if (this.correct_data) {
            int j;
            MyPhaser phaser = new MyPhaser(1, amount_of_phases, dec, amount_of_boxes);
            System.out.println("\n**************Starting Phaser Demostration**************\n");

            for (j = 0; j < amount_of_workers; j++) {
                new Thread(new MyThr(phaser, "Worker " + (j + 1), dec, amount_of_boxes)).start();
            }

            while (!phaser.isTerminated()) {
                    phaser.arriveAndAwaitAdvance();
            }

            if (phaser.isTerminated()) {
                System.out.println("All workers gathered boxes");
                System.out.println("\n********************************************************\n");
            }
        }
    }
}

class MyPhaser extends Phaser {
    int numPhases;
    AtomicInteger gathered_boxes_counter;
    int amount_of_boxes;
    Phaser ph;

    MyPhaser(int parties, int phaseCount, AtomicInteger gathered_boxes_counter, int amount_of_boxes) {
        super(parties);
        numPhases = phaseCount - 1;
        this.gathered_boxes_counter = gathered_boxes_counter;
        this.amount_of_boxes = amount_of_boxes;
        ph = this;
    }

    protected boolean onAdvance(int phase, int registeredParties) {
        System.out.println("Phase " + (phase + 1) + " completed\n");
        return (phase == numPhases || registeredParties == 0);
    }
}

class MyThr implements Runnable {
    MyPhaser phaser;
    String name;
    AtomicInteger queue;
    int amount_of_boxes;
    boolean go = true;

    MyThr(MyPhaser phaser, String name, AtomicInteger queue, int amount_of_boxes) {
        this.phaser = phaser;
        this.name = name;
        this.queue = queue;
        this.amount_of_boxes = amount_of_boxes;
        phaser.register();
    }

    public void run() {
        while(!phaser.isTerminated()) {
            System.out.println(name + " gathered a box of apples number " + (phaser.getPhase() + 1));
            phaser.arriveAndAwaitAdvance();
        }
    }
}