package org.iproute.thread0.demo.sync3.test022;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal 线程局部变量
 *
 * @author tech@intellij.io
 */
public class ThreadLocal1 {

    volatile static Person person = new Person();

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("person's name :" + person.name);
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            person.name = "hello";
        }).start();
    }

    static class Person {
        String name = "zhangsan";
    }

}


