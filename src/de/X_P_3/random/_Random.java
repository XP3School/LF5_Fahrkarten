package de.X_P_3.random;

import java.util.Random;

public class _Random {
    public static Random random = new Random();
    public static int getRandIntBetween(int min, int max){
        return random.nextInt(max - min) + min;
    }
}
