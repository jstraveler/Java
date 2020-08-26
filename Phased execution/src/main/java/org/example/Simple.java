package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Simple implements Demonstration{
    int amount_of_workers = 0;
    int amount_of_boxes = 0;
    int amount_of_phases = 0;
    boolean is_float = false;
    int float_part = 0;
    boolean correct_data = false;
    AtomicInteger resource = new AtomicInteger(0);
    LinkedList<Integer> boxes = new LinkedList<Integer>();


    Simple(int amount_of_workers, int amount_of_boxes){
        this.amount_of_workers = amount_of_workers;
        this.amount_of_boxes = amount_of_boxes;
        try {
            calculate_parameters();
        } catch (ArithmeticException e) {
            System.out.println("Data was entered incorrectly");
            //e.printStackTrace();
        }
    }

    public void calculate_parameters(){
        if (amount_of_workers > 0 && amount_of_boxes > 0) { // Д.б. больше нуля
            amount_of_phases = (amount_of_boxes / amount_of_workers);
            correct_data = true;
            for(int i = 0; i < amount_of_workers; i++) {
                boxes.add(amount_of_phases);
            }
            if (amount_of_boxes % amount_of_workers != 0) { // Если кол-во ящиков не делится на кол-во работников без остатка
                amount_of_phases++;                              // Увеличиваем кол-во барьеров на 1
                float_part = (amount_of_boxes % amount_of_workers); // Определяем необходимое кол-во работников в неполном барьере (остаток от деления)
                is_float = true;
                for(int i = 0; i < float_part; i++){
                    boxes.set(i, (boxes.get(i)+1));
                }
            }
        }
        else{
            System.out.println("Amount of workers must be greater than zero");
        }
    }

    public void start_gather(){
        if(correct_data) {
            //CyclicBarrier barrier;
            System.out.println("\n***Starting CyclicBarrier Demonstration***\n");
            int j = 0;
            ArrayList<MyThreadS> workers = new ArrayList<MyThreadS>();
            // Цикл для барьеров с полным количеством работников

                //barrier = new CyclicBarrier(amount_of_workers, new BarAction(j));
                for (int i = 0; i < amount_of_workers; i++) {
                    //resource = j;
                    workers.add(new MyThreadS( "Worker " + (i + 1), resource, boxes.get(i)));
                    try {                                                                // Для красивого вывода
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("All workers gathered box in " + (j) + " barrier\n");
            for (j = 1; j < amount_of_phases; j++) {
                try {
                    wait();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                for (MyThreadS worker : workers) {
                    worker.resume();
                }

                System.out.println("All workers gathered box in " + (j) + " barrier\n");
            }
            // Если кол-во ящиков не делится на кол-во работников без остатка
            /*if (!is_float) {
                //barrier = new CyclicBarrier(amount_of_workers, new BarAction(j));
                for (int i = 0; i < amount_of_workers; i++) {
                    //resource = j;
                    new MyThreadS( "Worker " + (i + 1), resource, boxes);
                    try {                                                                // Для красивого вывода
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Если делится, то запускаем последний барьер с полным кол-вом работников
            else {
                //barrier = new CyclicBarrier(float_part, new BarAction((amount_of_phases - 1)));
                for (int i = 0; i < float_part; i++) {
                    //resource = j;
                    new MyThreadS( "Worker " + (i + 1), resource, boxes);
                    try {                                                                // Для красивого вывода
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }*/
            try {
                for (MyThreadS worker : workers) {
                    worker.t.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("All workers gathered boxes");
        }
    }
}

class MyThreadS implements Runnable {
    AtomicInteger resource;
    String name;
    Integer boxes;
    Thread t;
    boolean resume = true;
    boolean ready = true;
    int counter = 1;

    MyThreadS(String name, AtomicInteger resource, Integer boxes) {
        this.boxes = boxes;
        this.name = name;
        this.resource = resource;
        t = new Thread(this);
        t.start();
    }

    public boolean is_ready(){
        return ready;
    }

    public void resume(){
        resume = true;
    }

    public void run() {
        while(ready) {
            if(resume) {
                System.out.println(name + " gathered a box of apples number " + (counter));
                counter++;
                resume = false;

                if(counter == boxes){
                    ready = false;
                }
            }
            notify();
        }

    }
}
