package Pack;

import java.util.concurrent.Semaphore;

class ConsumerProducerSemaphore {
    int n;
    static ProducerS p;
    static ConsumerS c;

    ConsumerProducerSemaphore() {
        p = new ProducerS(this);
        c = new ConsumerS(this);
    }

    static Semaphore semConsumer = new Semaphore(0);
    static Semaphore semProducer = new Semaphore(1);

    void get() {
        try {
            semConsumer.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s get it with the help of the class - Semaphore: %d %n", Thread.currentThread().getName(), n);
        semProducer.release();
    }

    void put(int n) {
        try {
            semProducer.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.n = n;
        System.out.printf("%s put it with the help of the class - Semaphore: %d %n", Thread.currentThread().getName(), n);
        semConsumer.release();
    }
}

class ProducerS implements Runnable{
    ConsumerProducerSemaphore q;
    Thread t;
    ProducerS(ConsumerProducerSemaphore q){
        this.q = q;
        t = new Thread(this, "Produser");
        t.start();
    }

    @Override
    public void run(){
        int i = 0;
        while(i < 5){
            q.put(randomWithRange(0,100));
            i++;
        }
    }

    int randomWithRange(int min, int max) {
        int range = Math.abs(max - min) + 1;
        return (int)(Math.random() * range) + (Math.min(min, max));
    }
}

class ConsumerS implements Runnable {
    ConsumerProducerSemaphore q;
    Thread t;
    ConsumerS(ConsumerProducerSemaphore q){
        this.q = q;
        t = new Thread(this, "Consumer");
        t.start();
    }

    @Override
    public void run(){
        int i = 0;
        while(i < 5){
            q.get();
            i++;
        }
    }
}