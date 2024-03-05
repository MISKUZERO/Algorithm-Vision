package test;

import random.SquareMid;

/**
 * @author MiskuZero
 */
public class Test {


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            System.out.print((byte) SquareMid.next(8) + " ");
        }

    }
}
