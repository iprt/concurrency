package org.iproute.thread0.demo.sync2;

import java.util.concurrent.TimeUnit;

/**
 * 对业务写方法加锁
 * <p>
 * 对业务读方法不枷锁
 * <p>
 * 容易产生脏读问题 （dirty read）
 *
 * @author tech@intellij.io
 */
public class Account {

    private String name;

    private double balance;

    public synchronized void set(String name, double balance) {
        this.name = name;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.balance = balance;
    }

    public /*synchronized*/ double getBalance() {
        return balance;
    }

    public static void main(String[] args) {

        Account account = new Account();

        new Thread(() -> account.set("zhangsan", 100.0)).start();


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(account.getBalance());

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(account.getBalance());

    }

}
