package org.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BarrierTask {

    public static void test(int participantsCount, int limit) {
        CyclicBarrier master = new CyclicBarrier(participantsCount);
        ExecutorService completionService = Executors.newFixedThreadPool(participantsCount);

        for (int i = 0; i < limit; i++) {
            completionService.submit(new Task(i, master));
        }

        completionService.shutdown();

    }

    static class Task implements Runnable {

        private final CyclicBarrier barrier;
        private final int id;

        public Task(int id, CyclicBarrier barrier) {
            this.barrier = barrier;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                String name = Thread.currentThread().getName();
                System.out.println("Worker " + name + " hash: " + Thread.currentThread().hashCode() + " is on task " + id);
                barrier.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
