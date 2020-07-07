package com.sky.javademo.threadpool;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yuandunbin782
 * @ClassName UseThreadPool
 * @Description
 * @date 2020-03-09
 */
public class UseThreadPool {
    static class Worker implements Runnable {
        private String taskName;
        private Random r = new Random();

        public Worker(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " process the task : " + taskName);
            try {
                Thread.sleep(r.nextInt(100) * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class CallWorker implements Callable {
        private String taskName;
        private Random r = new Random();

        public CallWorker(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName() + " process the task : " + taskName);
            return Thread.currentThread().getName() + ":" + r.nextInt(100) * 5;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 4, 3, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(10), new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0; i < 6; i++) {
            Worker worker = new Worker("worker_" + i);
            threadPoolExecutor.execute(worker);
        }
        for (int i = 0; i < 6; i++) {
            CallWorker callWorker = new CallWorker("callworker_" + i);
            Future<String> result = threadPoolExecutor.submit(callWorker);
            System.out.println(result.get());
        }
        threadPoolExecutor.shutdown();


    }
}
