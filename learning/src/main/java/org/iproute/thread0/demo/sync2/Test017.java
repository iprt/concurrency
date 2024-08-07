package org.iproute.thread0.demo.sync2;

import java.util.concurrent.TimeUnit;

/**
 * @author tech@intellij.io
 */
public class Test017 {

    // 锁的信息是记录在堆内存里面的
    Object o = new Object();

    void m() {
        synchronized (o) {

            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(Thread.currentThread().getName());

            }

        }
    }

    public static void main(String[] args) {

        Test017 test = new Test017();

        new Thread(test::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Thread t2 = new Thread(test::m, "t2");


        // 锁的对象发生改变，所以t2线程得以执行，如果注释掉这句话，线程2永远不会得到锁执行
        test.o = new Object();

        t2.start();

    }


}
