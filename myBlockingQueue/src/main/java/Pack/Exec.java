package Pack;

/** --- Задача ---
  *
  * Напишите собственную реализацию блокирующей очереди java.util.concurrent.BlockingQueue, не используя имеющиеся.
  * Напишите юнит-тесты своей реализации.
  *
  * --- Описание, состав - 4 модулЯ ---
  * - модуль Exec - демонстрирует работу myBlockingQueue на примере работы потоков типа Producer (производитель) и
  *   Consumer (потребитель) с переменной типа myBlockingQueue.
  * - модуль myBlockingQueue - содержит класс myBlockingQueue с методами get и put, эти методы для доступа к переменной
  *   (блокирующей очереди). С помощью wait/notify организована взаимная блокировка методов put и get, так что бы нельзя
  *   было взять значеие из очереди, предварительно не добавив его туда и наоборот.
  * - модуль Producer - содержит класс Producer, который создает событие и помещает его в очередь на исполнение.
  * - модуль Consumer - содержит класс Consumer, который забирает из очереди событие для обработки.
  *
**/

public class Exec {
    public static void main(String[] args) {
        myBlockingQueue<Integer> myBlockingQueue1 = new myBlockingQueue<>();
        //myBlockingQueue<Integer> bq2 = new myBlockingQueue<>();
        Producer<Integer> pr1 = new Producer<>(myBlockingQueue1, 1, 2);
        Producer<Integer> pr2 = new Producer<>(myBlockingQueue1, 2, 2);
        Consumer<Integer> cm1 = new Consumer<>(myBlockingQueue1, 1);
        Consumer<Integer> cm2 = new Consumer<>(myBlockingQueue1, 2);
        Consumer<Integer> cm3 = new Consumer<>(myBlockingQueue1, 3);

        try {
            pr1.t.join();
            pr2.t.join();
            cm1.t.join();
            cm2.t.join();
            cm3.t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}