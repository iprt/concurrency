package org.iproute.thread0.th;

/**
 * @author tech@intellij.io
 */
public class VolatileVisibilitySample {

    private static volatile boolean initFlag = false;

    public static void refresh() {
        System.out.println("refresh data ...");
        initFlag = true;
        System.out.println("refresh data success ...");
    }

    public static void loadData() {
        while (!initFlag) {
        }
        System.out.println("当前线程 " + Thread.currentThread().getName() + "嗅探到 initFlag状态的改变");
    }


    public static void main(String[] args) {

        Thread threadA = new Thread(() -> {
            loadData();
        }, "threadA");

        threadA.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Thread threadB = new Thread(() -> {
            refresh();
        }, "threadB");

        threadB.start();
    }

}
