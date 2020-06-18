package com.trans.actional;

/**
 * Created by lenovo on 2019/11/25.
 */
public class Pass {
    static int j = 20;

    public void f(int i) {
        i = i * 2;
        j = j * 2;
    }

    public static void main(String[] args) {
        int i = 10;
        Pass p = new Pass();
        p.f(i);
        System.out.println("i = " + i);
        System.out.println("j = " + j);
        int k = 5;
        do {
            System.out.println("k = " + k);
            k--;
        } while (k != 0);
//        String.valueOf("a");
//        StringBuilder sb1 = new StringBuilder();
//        StringBuffer sb2 = new StringBuffer();
    }
}
