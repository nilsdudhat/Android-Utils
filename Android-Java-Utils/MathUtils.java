package com.udemy.javaexample;

import java.util.Random;

public class MathUtils {

    public static int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }
}
