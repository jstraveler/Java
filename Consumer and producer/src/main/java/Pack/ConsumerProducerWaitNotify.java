package Pack;

class ConsumerProducerWaitNotify {
    int n;
    boolean valueSet = false;
    static ProducerW p;
    static ConsumerW c;

    ConsumerProducerWaitNotify(){
        p = new ProducerW(this);
        c = new ConsumerW(this);
    }

    synchronized void get() {
        while(!valueSet) {
            try{
                wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.printf("%s get it with the help of methods - wait/notify: %d %n", Thread.currentThread().getName(), n);
        valueSet = false;
        notify();
    }

    synchronized void put(int n){
        while(valueSet) {
            try {
                wait();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        this.n = n;
        valueSet = true;
        System.out.printf("%s put it with the help of methods - wait/notify: %d %n", Thread.currentThread().getName(), n);
        notify();
    }
}

class ProducerW implements Runnable{
    ConsumerProducerWaitNotify q;
    Thread t;
    ProducerW(ConsumerProducerWaitNotify q){
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

class ConsumerW implements Runnable {
    ConsumerProducerWaitNotify q;
    Thread t;
    ConsumerW(ConsumerProducerWaitNotify q){
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
