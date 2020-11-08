package com.sfjava.less1;

import java.util.Arrays;

/** --- Задание ---
 * Запишите в двумерный массив NxN случайные числа от 10 до 99. Размерность N получить из аргументов.
 * - Напишите обобщенную функцию возвращающую одномерный массив из переданного двумерного.
 * - Напишите обобщенную функцию, находящую среднее значение элементов переданного массива.
 * Протестируйте обе функции.
 *
 * TODO: How to prevent passing an uninitialized variable into a method? - DONE by validateArray
 * TODO: Make methods with generics - DONE
 * TODO: Make tests - DONE
 * TODO: add log4j for logging - DONE
 * How to check a string for a number - https://www.baeldung.com/java-check-string-number
 * How to print an array - https://www.javatpoint.com/how-to-print-array-in-java
 * About log4j https://logging.apache.org/log4j/2.x/manual/configuration.html
 * About log4j https://coderlessons.com/tutorials/java-tekhnologii/uchitsia-log4j/log4j-kratkoe-rukovodstvo
 *
 * Used java version:
 * openjdk version "13.0.2" 2020-01-14
 * OpenJDK Runtime Environment (build 13.0.2+8)
 * OpenJDK 64-Bit Server VM (build 13.0.2+8, mixed mode, sharing)
 */

public class Main {
    public static void main(String[] args) {

        ArrayConverter arrayConv = new ArrayConverter();
        int size = arrayConv.getMatrixDimension();

        Long[][] matLong = new Long[0][];
        matLong = (Long[][]) ArrayConverter.fillRandom(size, matLong);

        ArrayConverter.arrayPrint(matLong);

        long average = arrayConv.getAverage(matLong);
        System.out.println("Average is " + average);

        Long[] array = (Long[]) arrayConv.convertArray(matLong);
        System.out.println(Arrays.toString(array));


    }
}


/*Number[][] matInteger = new Integer[0][];
        matInteger = ArrayConverter.fillRandom(size, matInteger);
        Number[][] matDouble = new Double[0][];
        matDouble = ArrayConverter.fillRandom(size, matDouble);
        Number[][] matByte = new Byte[0][];
        matByte = ArrayConverter.fillRandom(size, matByte);
        Number[][] matFloat = new Float[0][];
        matFloat = ArrayConverter.fillRandom(size, matFloat);
        Number[][] matShort = new Short[0][];
        matShort = ArrayConverter.fillRandom(size, matShort);*/