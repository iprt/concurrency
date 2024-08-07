package org.iproute.eventloop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.Callable;

/**
 * EventLoopGroupMain
 *
 * @author tech@intellij.io
 * @since 5/7/2023
 */
@Slf4j
public class EventLoopGroupMain {
    static final EventLoopGroup group = new EventLoopGroup(10);

    public static void main(String[] args) throws InterruptedException {
        groupInGroupInGroup();

        Thread.sleep(10_000);
        group.terminate();
    }


    static void groupInGroupInGroup() {
        group.route("group in group first",
                () -> group.route("group in group second",
                        () -> group.route("group in group third",
                                () -> group.route("group in group fourth",
                                        () -> group.route("group in group fifth", new MyTask())
                                )
                        )
                )
        );
    }

    static void groupInGroup() throws InterruptedException {
        for (int c = 0; c < 10; c++) {
            Thread.sleep(1_000);
            group.route("put task", () -> {
                for (int i = 0; i < 100; i++) {
                    String result = group.route("group route group", new MyTask());
                    log.info("execute result is {}", result);
                }
                return "group in group";
            });
        }
    }


    static void simple() {
        for (int i = 0; i < 500; i++) {
            String result = group.route("add a+b", new MyTask());
            log.info("execute result is {}", result);
        }
    }


    private static class MyTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            int a = RandomUtils.nextInt(0, 10000);
            int b = RandomUtils.nextInt(0, 10000);

            log.info("a + b = {}", a + b);

            return "a + b = " + (a + b);
        }
    }

}
