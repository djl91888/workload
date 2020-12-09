package com.yuxin.test;

import org.junit.Test;

public class MainTest {

    @Test
    public void commonTest() {
        String date = "20201203";
        String month = date.substring(4, 6);
        System.out.println(month);
    }

    @Test
    public void threadTest() {
        for (int i = 0; i < 6; i++) {
            MyThread thread = new MyThread();
            thread.start();
        }
    }

}

class MyThread extends Thread {
    @Override
    public void run() {
        Hello hello = Hello.getInstance();
        System.out.println(hello);
    }
}

class Hello {
    private static Hello instance;
    public static Hello getInstance() {
        if (instance == null) {
            instance = new Hello();
        }
        return instance;
    }
    private Hello() {
    }
}
