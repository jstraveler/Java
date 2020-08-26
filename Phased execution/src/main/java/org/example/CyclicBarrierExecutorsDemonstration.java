package org.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierExecutorsDemonstration implements Demonstration {
    int amount_of_workers = 0;
    int amount_of_boxes = 0;
    int amount_of_phases = 0;
    boolean is_float = false;
    int float_part = 0;
    boolean correct_data = false;
    int resource;

    CyclicBarrierExecutorsDemonstration (int amount_of_workers, int amount_of_boxes) {
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
        } else {
            System.out.println("Amount of workers must be greater than zero");
        }
    }

    public void start_gather() {
        if(correct_data) {
            CyclicBarrier barrier;
            ExecutorService completionService = Executors.newFixedThreadPool(amount_of_workers);
            System.out.println("\n*****Starting CyclicBarrier Executors Demonstration*****\n");
            int j;
            // Цикл для барьеров с полным количеством работников
            for (j = 0; j < amount_of_phases - 1; j++) {
                barrier = new CyclicBarrier(amount_of_workers, new BarrierAction(j));
                for (int i = 0; i < amount_of_workers; i++) {
                    resource = j;
                    Box w = new Box(resource, barrier, amount_of_workers, "Worker ");
                    completionService.submit(w);
                    try {                                                                // Для красивого вывода
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Если кол-во ящиков не делится на кол-во работников без остатка
            if (!is_float) {
                barrier = new CyclicBarrier(amount_of_workers, new BarrierAction(j));
                for (int i = 0; i < amount_of_workers; i++) {
                    resource = j;
                    Box w = new Box(resource, barrier, amount_of_workers, "Worker ");
                    completionService.submit(w);
                    try {                                                                // Для красивого вывода
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Если делится, то запускаем последний барьер с полным кол-вом работников
            else {
                barrier = new CyclicBarrier(float_part, new BarrierAction((amount_of_phases - 1)));
                for (int i = 0; i < float_part; i++) {
                    resource = j;
                    Box w = new Box(resource, barrier, amount_of_workers, "Worker ");
                    completionService.submit(w);
                    try {                                                                // Для красивого вывода
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            completionService.shutdown();
            System.out.println("All workers gathered boxes");
            System.out.println("\n********************************************************\n");
        }
    }
}

class Box implements Runnable {
    private CyclicBarrier barrier;
    private int id;
    private int amount_of_workers;
    private int counter = 1;
    private String name;

    public Box(int id, CyclicBarrier barrier, int amount_of_workers, String name) {
        this.barrier = barrier;
        this.id = id;
        this.amount_of_workers = amount_of_workers;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            String Thread_name = Thread.currentThread().getName();
            Thread.currentThread().setName(Thread_name + " " + counter);
            counter++;
            System.out.println(name + Thread.currentThread().hashCode() + " gathered a box of apples number " + (id+1));
            barrier.await();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class BarrierAction implements Runnable {
    int Number_of_barrier;

    BarrierAction(int Number_of_barrier) {
        this.Number_of_barrier = Number_of_barrier;
    }

    public void run() {
        System.out.println("All workers gathered box in " + (Number_of_barrier + 1) + " barrier\n");
    }
}
