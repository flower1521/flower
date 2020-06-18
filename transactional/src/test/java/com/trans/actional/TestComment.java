package com.trans.actional;

/**
 * create by lcl on 2020/1/7 17:46
 */
public class TestComment {
    public static void main(String[] args) {
        String comment = "COMMENT 'xxx'";
        int start = comment.indexOf("'");
        int end = comment.lastIndexOf("'");
        String middle = comment.substring(start + 1, end);
        System.out.println(middle);//xxx
        comment = comment.replace(middle, "");
        System.out.println(comment);//COMMENT ''
    }
}
