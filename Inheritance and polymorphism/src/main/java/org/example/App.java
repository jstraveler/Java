package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * ЗАДАНИЕ:
 * Создать приложение с 6 классами и 3 интерфейсами.
 * Все интерфейсы унаследовать от интерфейса "Предмет" с методом цена.
 * - Интерфейсы: "Игрушка" с методом играть, "Дом" с методом жить, "Еда" с методом есть.
 * - Классы - "Мячик", "Кукла"; "Котлета", "Бутерброд"; "Юрта", "Коттедж"; Каждый класс реализует соответствующий интерфейс.
 * В приложении создать массив объектов "Предмет", состоящий из объектов всех классов (количество элементов задано параметром).
 * Вывести все Предметы на дисплей. Перебирая в цикле элементы массива "Предмет", находить c помощью оператора instanceof
 * те объекты, которые реализуют интерфейс "Еда". Для каждого найденного элемента массива "Предмет", реализующего интерфейс "Еда",
 * выполнить метод интерфейса "Еда" и метод интерфейса "Предмет". Вывести результаты поиска.

 */
public class App {
    public static void main( String[] args ) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int count_of_objects = 0;
        try {
            count_of_objects = Integer.parseInt(reader.readLine());
        }
        catch(IOException e){
            e.printStackTrace();
        }

        ArrayList<Subject> sub = new ArrayList<>();

        set_Array(sub, count_of_objects);

        for(Subject subject : sub) {
            if(subject instanceof Food){
                ((Food) subject).eat();
                subject.price();
            }
        }
    }

    static void set_Array(ArrayList<Subject> sub, int count_of_objects) {
        int a = 1;
        for(int i = 1; i <= count_of_objects; i++) {
            if(a > 6){
                a = 1;
            }
            switch(a) {
                case 1 :
                    sub.add(new Ball());
                    break;
                case 2 :
                    sub.add(new Doll());
                    break;
                case 3 :
                    sub.add(new Cutlet());
                    break;
                case 4 :
                    sub.add(new Sandwich());
                    break;
                case 5 :
                    sub.add(new Cottage());
                    break;
                case 6 :
                    sub.add(new Yurt());
                    break;
                default :
                    System.out.println("Out of range");
            }
            a++;
        }
    }
}