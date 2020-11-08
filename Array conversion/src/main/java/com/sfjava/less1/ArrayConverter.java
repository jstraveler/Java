package com.sfjava.less1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for convert arrays with Generics methods
 */

public class ArrayConverter {
    private final static Logger logger = Logger.getLogger(ArrayConverter.class.getName());

    ArrayConverter() {
        //System.out.println("Create ArrayConverter object");
        logger.log(Level.INFO, "Create ArrayConverter object");
    }

    /** Method for read from console a number and create variable dimension for initialized array */
    public int getMatrixDimension() {
        int dimension = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Enter the number: ");
            String s = reader.readLine();
            if(isNumeric(s)) {
                dimension = Integer.parseInt(s);
            }
            else {
                //System.out.println("The entered value must be a number");
                logger.log(Level.WARNING, "The entered value must be a positive number");
            }
        }
        catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return dimension;
    }

    /** Method for convert two dimensional array to one dimensional array */
    public <T> Number[] convertArray(T[][] matrix) {
        Number[] convertedArray = new Long[0];
        if (validateArray(matrix)) {
            int rows = matrix.length; // Number of rows
            int columns = matrix[0].length; // Number of columns
            convertedArray = new Long[rows * columns];
            for (int i = 0; i < rows; i++) { //System.arraycopy(array[i], 0, convertedArray, i * rows, columns);
                for (int j = 0; j < columns; j++) {
                    convertedArray[i * rows + j] = (Long) matrix[i][j];
                }
            }
        }
        else {
            //System.out.println("Unvalidated array");
            logger.log(Level.WARNING, "Unvalidated array");
        }
        return convertedArray;
    }

    /** Method for calculating the average in an array */
    public <T> long getAverage(T[][] matrix) {
        long average = 0;
        if (validateArray(matrix)) {
            int rows = matrix.length; // Number of rows
            int columns = matrix[0].length; // Number of columns
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    average = average + (long) matrix[i][j]; //
                }
            }
            BigDecimal bd = new BigDecimal(Double.toString((average / (double) (rows * columns))));
            bd = bd.setScale(0, RoundingMode.HALF_UP);
            return (long) bd.doubleValue();
        }
        else {
            //System.out.println("Unvalidated array");
            logger.log(Level.WARNING, "Unvalidated array");
        }
        return average;
    }

    /** Method for filling an array with random values */
    // If the array isn't initialized, we can get a NullPointerExecption
    // If the array has size 0, we can get ArrayIndexOutOfBoundsException
    // Class java.util.Arrays have method fill
    public static <T> Number[][] fillRandom(int size, T[][] mat) {
        Number[][] matrix = create(size, mat);
        if (validateArray(matrix)) {
            int rows = matrix.length; // Number of rows
            int columns = matrix[0].length; // Number of columns
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    matrix[i][j] = (long) (89.0 * new Random().nextDouble() + 10.0); //
                }
            }
        }
        else {
            //System.out.println("Unvalidated array");
            logger.log(Level.WARNING, "Unvalidated array");
        }
        return matrix;
    }

    private static <T> Number[][] create(int size, T[][] mat) {
        Number[][] matrix;
        switch (mat.getClass().getSimpleName()){
            case("Long[][]"):
                matrix = new Long[size][size];
                System.out.println(matrix.getClass().getSimpleName());
                break;
            case("Integer[][]"):
                matrix = new Integer[size][size];
                break;
            case("Float[][]"):
                matrix = new Float[size][size];
                break;
            case("Double[][]"):
                matrix = new Double[size][size];
                break;
            case("Byte[][]"):
                matrix = new Byte[size][size];
                break;
            case("Short[][]"):
                matrix = new Short[size][size];
                break;
            default:
                matrix = new Number[size][size];
                break;
        }
        return matrix;
    }

    /** Method for printing array */
    public static <T> void arrayPrint(T[][] matrix) {
        if (validateArray(matrix)) {
            StringBuilder row;
            int rows = matrix.length; // Number of rows
            int columns = matrix[0].length; // Number of columns
            for (int i = 0; i < rows; i++) {
                row = new StringBuilder("[");
                for (int j = 0; j < columns; j++) {
                    if (j == (columns - 1)) {
                        row.append(matrix[i][j]);
                    } else {
                        row.append(matrix[i][j]).append(", ");
                    }
                }
                System.out.println(row.append("]"));
            }
        }
        else {
            //System.out.println("Unvalidated array");
            logger.log(Level.WARNING, "Unvalidated array");
        }
    }

    /** Method for check array */
    private static <T> boolean validateArray(T[][] matrix) {
        boolean isValidatedArray;
        if (matrix == null) {
            isValidatedArray = false;
            //System.out.println("Array isn't initialized");
            logger.log(Level.WARNING, "Array isn't initialized");
        }
        else if (matrix.length <= 0) {
            isValidatedArray = false;
            //System.out.println("Array size is 0");
            logger.log(Level.INFO, "Array size is 0");
        }
        else {
            isValidatedArray = true;
        }
        return  isValidatedArray;
    }


    /** Method for check a string for a number */
    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            //System.out.println("Nothing is entered");
            logger.log(Level.WARNING, "Nothing is entered");
            return false;
        }
        long d;
        try {
            d = Long.parseLong(strNum);
        }
        catch (NumberFormatException e) {
            //System.out.println("The entered value must be a number");
            logger.log(Level.WARNING, "The entered value must be a number");
            return false;
        }
        if(d <= 0){
            //System.out.println("Enter the positive number");
            logger.log(Level.WARNING, "Enter the positive number");
            return false;
        }
        else {
            return true;
        }
    }
}
