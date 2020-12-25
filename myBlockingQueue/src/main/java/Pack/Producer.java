package Pack;

import java.util.logging.Logger;

public class Producer<E> implements Runnable {
    private final static Logger logger = Logger.getLogger(Producer.class.getName());
    Thread t;
    myBlockingQueue<E> myBlockingQueue;
    Integer event;
    Integer oppToUse;
    public Producer(myBlockingQueue<E> myBlockingQueue, Integer n, Integer oppToUse) {
        this.myBlockingQueue = myBlockingQueue;
        this.oppToUse = oppToUse;
        t = new Thread(this, "Produser-" + n);
        t.start();
    }

    @Override
    public void run() {
        int i = 0;
        while (oppToUse > 0) {
            myBlockingQueue.put(createEvent()); // thread will block here
            oppToUse--;
            System.out.printf("The %s published the event: %s %n", Thread.currentThread().getName(), event);
            //logger.log(Level.FINEST, "The " + Thread.currentThread().getName() + " published the event: " + event);
            i++;
        }
        myBlockingQueue.remove();
    }

    public E createEvent() {
        event = randomWithRange(0, 100);
        /*BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            event = Integer.parseInt(reader.readLine());
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
        return (E) event;
    }

    int randomWithRange(int min, int max) {
        int range = Math.abs(max - min) + 1;
        return (int)(Math.random() * range) + (Math.min(min, max));
    }
}
