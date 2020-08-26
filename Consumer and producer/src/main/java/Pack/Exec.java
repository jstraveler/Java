package Pack;

/** --- Задача ---
  *
  * Напишите приложение с 2 потоками - производителем и потребителем, которые имеют разделяемую статическую переменную - склад.
  * То есть склад не является массивом! Склад хранит целые числа от 0 до 100.
  * Производитель генерирует число от 0 до 100 и отправляет его на склад, выводя имя производителя и сгенерированное число.
  * Потребитель читает значение числа со склада и выводит на консоль имя потребителя и полученное число.
  * Не используйте любые задержки для потоков после начала их работы в виде методов sleep, vield или wait c параметром.
  * Неработающий поток находится в состоянии ожидания сигнала от другого потока и продолжает работу только когда такой
  * сигнал получен.
  *
  * --- Описание, состав - 4 модуля ---
  *
  *   Все три модуля реализуют одну и туже логику разными способами.
  * - модуль ConsumerProducerSemaphore - содержит класс ConsumerProducerSemaphore с методами get и put, классы
  *   ProducerS (производитель) и ConsumerS (потребитель) использующие методы выше описанного класса. Для реализации
  *   логики производитель/потребитель используется объект класса Semaphore. Этот класс реализует возможность управления
  *   разрешением на доступ к ресурсу, используя методы acquire (получить разрешение) и release (освободить разрешение).
  * - модуль ConsumerProducerWaitNotify - содержит класс ConsumerProducerWaitNotif с синхронизированными методами get и put,
  *   классы ProducerW (производитель) и ConsumerW (потребитель). Последовательная работа потоков с ресурсом реализована
  *   за счет работы методов wait и notify. Wait приостанавливает работу вызвавшего потока до тех пор, пока в другом
  *   потоке не будет вызван метод notify.
  * - модуль ConsumerProducerSynchronousQueue - содержит класс ConsumerProducerSynchronousQueue в котором хранится ссылка
  *   на синхронизированную очередь типа SynchronousQueue, классы ProducerQ (производитель) и ConsumerQ (потребитель) между
  *   потоками этих типов осуществляется передача значения путем помещения и извлечения его из синхронизированной очереди.
  *   Класс SynchronousQueue реализован таким образом, что в методах put и get осуществляется блокировка очереди и
  *   гарантируется последовательный доступ потоков к ней, потребитель не заберет элемент, пока производитель его туда
  *   не поместит, так же не будет осуществляться доступ к "пустой" очереди и есть защита от повторного помещения элемента в очередь.
  * - модуль Exec - содержит класс Exec (Execution) с методом main в котором создаются объекты всех описанных классов и
  *   демонстрируется работа каждого из них по очередности.
**/

public class Exec {
    public static void main(String[] args) {
         System.out.println("**********Synchronous Queue Demonstration*********");
        new ConsumerProducerSynchronousQueue();

        try {
            ConsumerProducerSynchronousQueue.p.t.join();
            ConsumerProducerSynchronousQueue.c.t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("**************************************************");
        System.out.println("**********Semaphore Demonstration*****************");
        new ConsumerProducerSemaphore();

        try {
            ConsumerProducerSemaphore.p.t.join();
            ConsumerProducerSemaphore.c.t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("**************************************************");
        System.out.println("**********Wait Notify Demonstration***************");
        new ConsumerProducerWaitNotify();

        try {
            ConsumerProducerWaitNotify.p.t.join();
            ConsumerProducerWaitNotify.c.t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("**************************************************");
    }
}