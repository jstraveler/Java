package com.sfjava.less1;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void fillRandom() {
        //given
        //ArrayConverter arrayConv = new ArrayConverter();

        //run
        Number[][] matrix = new Long[0][];
        matrix = ArrayConverter.fillRandom(2, matrix);

        //assert
        Assert.assertEquals(2, matrix.length);
    }

    @Test
    void convertArray() {
        //given
        ArrayConverter arrayConv = new ArrayConverter();
        Long[][] matrix = new Long[][]{{1L,2L},{3L,4L}};
        Long[] array = new Long[]{1L,2L,3L,4L};

        //run
        Long[] checkedArray = (Long[]) arrayConv.convertArray(matrix);

        //assert
        Assert.assertArrayEquals(array, checkedArray);
    }

    @Test
    void getAverage() {
        //given
        ArrayConverter arrayConv = new ArrayConverter();
        Long[][] matrix = new Long[][]{{1L,2L},{3L,4L}};
        long average = 3L;

        //run
        long checkedAverage = arrayConv.getAverage(matrix);

        //assert
        Assert.assertEquals(average, checkedAverage);
    }
}