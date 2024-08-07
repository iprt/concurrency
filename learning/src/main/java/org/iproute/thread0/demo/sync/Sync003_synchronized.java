package org.iproute.thread0.demo.sync;

/**
 * @author tech@intellij.io
 */
public class Sync003_synchronized {

    private int count = 10;

    // 这一段等价于 Sync003 里面的 m() 方法
    private synchronized void m() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}
