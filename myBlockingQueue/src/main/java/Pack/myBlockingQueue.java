package Pack;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class myBlockingQueue<E> {
    private final static Logger logger = Logger.getLogger(myBlockingQueue.class.getName());
    private final ArrayList<E> queue = new ArrayList<>(1);
    private boolean valueSet = false;
    private boolean instance;

    public myBlockingQueue() {
        this.instance = true;
        logger.log(Level.INFO, "Create queue");
    }

    E peek(E e) {
        return null;
    }

    synchronized public void put(E e) {
        if (e == null) throw new NullPointerException();
        while(valueSet) {
            try {
                wait();
            } catch (InterruptedException exc){
                exc.printStackTrace();
            }
        }
        if(queue.isEmpty()) {
            queue.add(0, e);
        } else {
            throw new IllegalArgumentException("Queue isn't empty");
        }
        valueSet = true;
        System.out.printf("%s put it %n", Thread.currentThread().getName());
        //logger.log(Level.INFO, Thread.currentThread().getName() + " put it");
        notify();
    }

    synchronized public E get() {
        E e;
        while(!valueSet) {
            try{
                wait();
            } catch(InterruptedException exc){
                exc.printStackTrace();
            }
        }
        if (!queue.isEmpty()) {
             e = queue.get(0);
             queue.clear();
        } else {
            throw new IllegalArgumentException("Queue is empty");
        }
        valueSet = false;
        System.out.printf("%s get it %n", Thread.currentThread().getName());
        //logger.log(Level.INFO, Thread.currentThread().getName() + " get it");
        notify();
        return e;
    }

    synchronized boolean isEmpty() {
        return instance;
    }

    synchronized void remove() {
        if (instance & queue.isEmpty()) {
            queue.clear();
            instance = false;
            logger.log(Level.INFO, "Queue is clear");
        }
    }
}
