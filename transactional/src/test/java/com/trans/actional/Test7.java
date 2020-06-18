package com.trans.actional;

/**
 * Created by lenovo on 2019/11/25.
 */
public class Test7 {
    public static void main(String[] args) {
        Y y1 = new Y();
        Y y2 = new Y();
    }
}

class X {
    static {
        System.out.println("X的静态代码块");
    }

    {
        System.out.println("X的构造代码块");
    }

    public X() {
        System.out.println("X的无参构造器");
    }
}

class Y extends X {
    static {
        System.out.println("Y的静态代码块");
    }

    {
        System.out.println("Y的构造代码块");
    }

    public Y() {
        System.out.println("Y的无参构造器");
    }
}