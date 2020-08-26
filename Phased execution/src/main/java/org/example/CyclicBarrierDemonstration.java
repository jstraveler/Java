package org.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class CyclicBarrierDemonstration implements Demonstration {
    int amount_of_workers = 0;
    int amount_of_boxes = 0;
    int amount_of_phases = 0;
    boolean is_float = false;
    int float_part = 0;
    boolean correct_data = false;
    int resource;

    CyclicBarrierDemonstration(int amount_of_workers, int amount_of_boxes) {
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
        if (amount_of_workers > 0 && amount_of_boxes > 0) { // Д.б. больше нуля
            amount_of_phases = (amount_of_boxes / amount_of_workers);
            correct_data = true;
            if (amount_of_boxes % amount_of_workers != 0) { // Если кол-во ящиков не делится на кол-во работников без остатка
                amount_of_phases++;                              // Увеличиваем кол-во барьеров на 1
                float_part = (amount_of_boxes % amount_of_workers); // Определяем необходимое кол-во работников в неполном барьере (остаток от деления)
                is_float = true;
            }
        }
        else{
            System.out.println("Amount of workers must be greater than zero");
        }
    }

    public void start_gather() {
        if(correct_data) {
            CyclicBarrier barrier;
            System.out.println("\n**********Starting CyclicBarrier Demonstration**********\n");
            int j;
            // Цикл для барьеров с полным количеством работников
            for (j = 0; j < amount_of_phases - 1; j++) {
                barrier = new CyclicBarrier(amount_of_workers, new BarAction(j));
                for (int i = 0; i < amount_of_workers; i++) {
                    resource = j;
                    new MyThread(barrier, "Worker " + (i + 1), resource);
                    try {                                                                // Для красивого вывода
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Если кол-во ящиков не делится на кол-во работников без остатка
            if (!is_float) {
                barrier = new CyclicBarrier(amount_of_workers, new BarAction(j));
                for (int i = 0; i < amount_of_workers; i++) {
                    resource = j;
                    new MyThread(barrier, "Worker " + (i + 1), resource);
                    try {                                                                // Для красивого вывода
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Если делится, то запускаем последний барьер с полным кол-вом работников
            else {
                barrier = new CyclicBarrier(float_part, new BarAction((amount_of_phases - 1)));
                for (int i = 0; i < float_part; i++) {
                    resource = j;
                    new MyThread(barrier, "Worker " + (i + 1), resource);
                    try {                                                                // Для красивого вывода
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("All workers gathered boxes");
            System.out.println("\n********************************************************\n");
        }
    }
}

class MyThread implements Runnable {
    CyclicBarrier barrier;
    String name;
    int resource;
    Thread t;

    MyThread(CyclicBarrier barrier, String name, int resource) {
        this.barrier = barrier;
        this.name = name;
        this.resource = resource;
        t = new Thread(this);
        t.start();
    }

    public void run() {
        System.out.println(name + " gathered a box of apples number " + (resource + 1));
        try {
            barrier.await();
        }
        catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class BarAction implements Runnable {
    int Number_of_barrier;

    BarAction(int Number_of_barrier) {
        this.Number_of_barrier = Number_of_barrier;
    }

    public void run() {
        System.out.println("All workers gathered box in " + (Number_of_barrier + 1) + " barrier\n");
    }
}