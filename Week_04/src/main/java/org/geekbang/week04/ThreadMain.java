package org.geekbang.week04;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 作业：思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程
 *
 * 思路
 *
 * 逻辑无非就是主线程能等待子线程执行完赋值操作，再做打印
 *
 * 1. 如何传值，大同小异，无非就是：
 *
 * 共享内存（队列，变量，对象引用），或者共享外部资源（数据库，文件系统，缓存框架），外部服务（socket，
 * http） 等...
 *
 * 2. 重点在于主线程如何监控到子线程的任务完成状态，我能想到的：
 *
 * 2.1 Java 线程交互的本质翻到底就是 await,signal; wait/notify 了，诸如 join，future，
 * CountDownLatch，cyclicBarrier，semaphore等等...
 * 万变不离其宗
 *
 * 2.2 还有一种 while true 这有点像自旋，用一个线程安全的共享变量连接父子线程，不断监控子线程的操作
 * 状态，然后父线程等到子线程完成后再执行之后的逻辑
 */
public class ThreadMain {

    public static void main(String[] args) throws Exception {

        // 1 三种实现本质都是基于 join，只是传值的方式变了而已
        //joinExample1();
        //joinExample2();
        //joinExample3();
        //
        //// 2
        //callableExample();
        //
        //// 3
        //futureTaskExample();
        //
        //// 4
        //countDownLatchExample();
        //
        //// 5
        //cyclicBarrierExample();

        // 6
        //semaphoreExample();

        // 7 外部存储，文件，数据库，NoSQL，缓存框架，其他远程服务...也是 join
        //storageExample();

        // 8 queue
        //queueExample();

        // 9 lock
        lockExample();
    }

    /**
     * 使用 join 1 封装对象，自定义方法
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

    /**
     * threadLocal
     */
    public static void cyclicBarrierExample() throws InterruptedException, BrokenBarrierException {
        StringBuilder result = new StringBuilder();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, Thread.currentThread());
        Runnable task = () -> {
            result.append("使用 CyclicBarrier");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        cyclicBarrier.await();
        System.out.println(result.toString());
    }

    /**
     * semaphore
     */
    public static void semaphoreExample() throws InterruptedException {
        StringBuffer result = new StringBuffer();
        int num = 1;
        Semaphore semaphore = new Semaphore(num);
        Runnable task = () -> {
            try {
                semaphore.acquire();
                result.append("使用 semaphore");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        while (true) {
            if (semaphore.availablePermits() >= num && result.toString().length() > 0) {
                System.out.println(result.toString());
                break;
            } else {
                TimeUnit.MICROSECONDS.sleep(30);
            }
        }
    }

    /**
     *
     * 使用外部存储
     *
     * 读取文件，数据库，NoSql，缓存框架等一切外部设备都属于一类东西
     * */
    public static void storageExample() throws IOException, InterruptedException {
        File file = File.createTempFile("storage", ".t");
        StorageService storageService = new StorageService(file);
        Runnable task = () -> {
            storageService.save("使用 storage");
        };
        Thread thread = new Thread(task);
        thread.start();
        thread.join();
        String load = storageService.load();
        System.out.println(load);
    }

    /**
     *
     * 使用队列
     *
     * */
    public static void queueExample() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        Runnable task = () -> {
            try {
                queue.put("使用 queue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        System.out.println(queue.take());
    }

    private static final ReentrantLock lock = new ReentrantLock();

    /**
     * 锁
     * */
    public static void lockExample() {
        StringBuffer result = new StringBuffer();
        Condition condition = lock.newCondition();

        Runnable task = () -> {
            try {
                lock.lock();
                result.append("使用 lock");
            } finally {
                condition.signalAll();
                lock.unlock();
            }
        };

        Thread thread = new Thread(task);
        thread.start();

        try {
            lock.lock();
            condition.await();
            System.out.println(result.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
