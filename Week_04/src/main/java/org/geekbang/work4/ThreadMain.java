package org.geekbang.work4;

import java.util.concurrent.*;

/**
 * 作业：思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程
 */
public class ThreadMain {

    public static void main(String[] args) throws Exception {
        callableExample();
        futureTaskExample();
        joinExample1();
        joinExample2();
        joinExample3();
        countDownLatchExample();
    }

    /**
     * 使用 Callable
     */
    public static void callableExample() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<String> task = () -> "使用 Callable";
        executor.submit(task);
        System.out.println(task.call());
        executor.shutdownNow();
    }

    /**
     * 使用 FutureTask
     */
    public static void futureTaskExample() throws InterruptedException, ExecutionException {
        Callable<String> callable = () -> "使用 FutureTask";
        FutureTask<String> task = new FutureTask<>(callable);
        Thread thread = new Thread(task);
        thread.start();
        System.out.println(task.get());
    }

    /**
     * 使用 join 1 自定义方法
     */
    public static void joinExample1() throws InterruptedException {
        SubThread task = new SubThread("使用 join 1 自定义方法");
        Thread thread = new Thread(task);
        thread.start();
        thread.join();
        System.out.println(task.getResult());
    }

    /**
     * 使用 join 2 对象引用
     */
    public static void joinExample2() throws InterruptedException {
        StringBuilder result = new StringBuilder();
        Runnable task = () -> result.append("使用 join 2 对象引用");
        Thread thread = new Thread(task);
        thread.start();
        thread.join();
        System.out.println(result.toString());
    }

    private static String result;

    /**
     * 使用 join 3 静态变量
     */
    public static void joinExample3() throws InterruptedException {
        Runnable task = () -> result = "使用 join 3 静态变量";
        Thread thread = new Thread(task);
        thread.start();
        thread.join();
        System.out.println(result);
    }

    /**
     * 使用 CountDownLatch
     */
    public static void countDownLatchExample() throws InterruptedException {
        StringBuilder result = new StringBuilder();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Runnable task = () -> {
            result.append("使用 CountDownLatch");
            countDownLatch.countDown();
        };
        Thread thread = new Thread(task);
        thread.start();
        countDownLatch.await();
        System.out.println(result.toString());
    }

}
