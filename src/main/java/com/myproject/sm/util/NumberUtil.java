package com.myproject.sm.util;

import java.util.Random;

public class NumberUtil {

    public static String getRandomNumberString() {
        // generate 6 random digit
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // format to string
        return String.format("%06d", number);
    }

}
