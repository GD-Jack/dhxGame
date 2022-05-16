package com.jack.util;

import java.util.Random;

public class SecurityCodeUtil {


    public static String getSecurityCode(int num) {
        String checkCode = "";
        int number;
        char code;
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            number = random.nextInt();
            if (number < 0) {
                number = -number;
            }
            if (number % 2 == 0) {
                code = (char) (48 + (number % 10));
            } else {
                code = (char) (65 + (number % 26));
            }
            checkCode += code;
        }
        return checkCode;
    }
}
