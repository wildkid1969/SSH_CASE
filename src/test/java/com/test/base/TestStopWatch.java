package com.test.base;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * @Author: mengya
 * @Date: 2021/3/9 10:54
 */
public class TestStopWatch {
    public static void main(String[] args) throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();

        Thread.sleep(1000);
        long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        Stopwatch stop = stopwatch.stop();
        System.out.println(elapsed);
        System.out.println(stop.elapsed(TimeUnit.MILLISECONDS));

        stopwatch.reset();
        stopwatch.start();
        Thread.sleep(1500);
        Stopwatch stop2 = stopwatch.stop();
        System.out.println(stop2.elapsed(TimeUnit.MILLISECONDS));

        stopwatch.reset();
        stopwatch.start();
        Thread.sleep(500);
        Stopwatch stop3 = stopwatch.stop();
        System.out.println(stop3.elapsed(TimeUnit.MILLISECONDS));
    }
}
