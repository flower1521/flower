package com.trans.actional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2019/11/27.
 */
public class AA extends A {
    public AA() {
        System.out.println("AA");
    }

    public void f() {
        System.out.println("AA f");
    }

    public static void main(String[] args) {
        A a = new AA();
        a.f();
//        A a = new A();
//        a.f();
        Set<String> set = new HashSet<String>();
        set.add("a");
    }
}
