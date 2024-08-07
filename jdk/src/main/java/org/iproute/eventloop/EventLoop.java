package org.iproute.eventloop;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;

/**
 * EventLoop 消费者
 * <p>
 * 1. 每个EventLoop都是一个线程 <br/>
 * 2. 每个EventLoop包含一个BlockingQueue <br/>
 * 3. 每个EventLoop中的线程run方法是个 while-true 循环 ， 两个判断条件： 线程的interrupt标志位 和 队列是否为空
 *
 * @author tech@intellij.io
 * @since 5/7/2023
 */
@Slf4j
public class EventLoop extends Thread {

    /**
     * step1
     * <p>
     * container
     */
    private final BlockingQueue<FutureTask<String>> queue;

    public EventLoop(BlockingQueue<FutureTask<String>> queue) {
        this.queue = queue;
    }


    /**
     * step2
     */
    @Override
    public void run() {
        // case1 当前线程没有被interrupted掉
        // case2 interrupt 了，但是队列不为空
        while (!Thread.interrupted() || (Thread.interrupted() && !queue.isEmpty())) {
            // consumer
            try {
                FutureTask<String> task = queue.take();
                executeTask(task);
            } catch (InterruptedException e) {
                log.info("MyEventLoop {} interrupted", Thread.currentThread().getName());
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * step3
     * <p>
     * Execute task
     *
     * @param task the task
     */
    static void executeTask(final FutureTask<String> task) {
        if (!task.isCancelled()) {
            try {
                task.run();

                // we ran it, but we have to grab the exception if raised
                task.get();
            } catch (Exception e) {
                log.error("MyEventLoop {} reached exception in processing command", Thread.currentThread().getName(), e);
                throw new RuntimeException(e);
            }
        }
    }

}
