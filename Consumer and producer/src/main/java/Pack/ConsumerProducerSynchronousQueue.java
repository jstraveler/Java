package Pack;

import java.util.concurrent.SynchronousQueue;

class ConsumerProducerSynchronousQueue {
    final static SynchronousQueue<Integer> queue = new SynchronousQueue<>();
    static ProduserQ p;
    static ConsumerQ c;
    ConsumerProducerSynchronousQueue() {
        p = new ProduserQ(queue);
        c = new ConsumerQ(queue);
    }
}

class ProduserQ implements Runnable {
    Thread t;
    SynchronousQueue<Integer> queue;
    ProduserQ(SynchronousQueue<Integer> queue) {
        this.queue = queue;
        t = new Thread(this, "Produser");
        t.start();
    }

    @Override
    public void run() {
        Integer event;
        try {
            int i = 0;
            while(i < 5) {
                event = randomWithRange(0, 100);
                queue.put(event); // thread will block here
                System.out.printf("The %s published the event with the help of SynchronousQueue: %s %n",
                        Thread.currentThread().getName(), event);
                i++;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int randomWithRange(int min, int max) {
        int range = Math.abs(max - min) + 1;
        return (int)(Math.random() * range) + (Math.min(min, max));
    }
}

class ConsumerQ implements Runnable {
    Thread t;
    SynchronousQueue<?> queue;
    ConsumerQ(SynchronousQueue<?> queue) {
        this.queue = queue;
        t = new Thread(this, "Consumer");
        t.start();
    }

    @Override
    public void run() {
        try {
            int i = 0;
            while(i < 5) {
                Integer event = (Integer) queue.take(); // thread will block here
                System.out.printf("The %s used the event with the help of SynchronousQueue     : %s %n",
                        Thread.currentThread().getName(), event);
                i++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}