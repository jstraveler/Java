package Pack;

import java.util.logging.Logger;

public class Consumer<E> implements Runnable {
    private final static Logger logger = Logger.getLogger(Consumer.class.getName());
    Thread t;
    myBlockingQueue<E> myBlockingQueue;
    Consumer(myBlockingQueue<E> myBlockingQueue, Integer n) {
        this.myBlockingQueue = myBlockingQueue;
        t = new Thread(this, "Consumer-" + n);
        t.start();
    }

    @Override
    public void run() {
        int i = 0;
        while (myBlockingQueue.isEmpty()) {
            String event = resolve((Integer) myBlockingQueue.get()); // thread will block here
            System.out.printf("The %s used the event     : %s %n", Thread.currentThread().getName(), event);
            //logger.log(Level.INFO, "The " + Thread.currentThread().getName() + " used the event     : " + event);
            i++;
        }
    }

    String resolve(Integer num) {
        String str;
        if (num >= 0 & num <= 10) {
            str = "Ten";
        } else if (num > 10 & num <= 20) {
            str = "Twenty";
        } else if (num > 20 & num <= 30) {
            str = "Thirty";
        } else if (num > 30 & num <= 40) {
            str = "Forty";
        } else if (num > 40 & num <= 50) {
            str = "Fifty";
        } else if (num > 50 & num <= 60) {
            str = "Sixty";
        } else if (num > 60 & num <= 70) {
            str = "Seventy";
        } else if (num > 70 & num <= 80) {
            str = "Eighty";
        } else if (num > 80 & num <= 90) {
            str = "Ninety";
        } else if (num > 90 & num <= 100) {
            str = "One hundred";
        } else {
            str = "Something else";
        }
        return str;
    }
}
