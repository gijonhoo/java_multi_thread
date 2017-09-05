package com.gijon.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock
 */
public class ReentrantLock01 extends Thread{
    private Lock lock = new ReentrantLock();

    private void methodA(){
        lock.lock();
        for(int i =0; i < 5; i++){
            System.out.println("ThreadName="+ Thread.currentThread().getName()+" "+i);
        }
        lock.unlock();
    }

    private void methodB(){
        try{
            lock.lock();
            System.out.println("ThreadName="+ Thread.currentThread().getName()+" method B start");
            Thread.sleep(1000);
            System.out.println("ThreadName="+ Thread.currentThread().getName()+" method B end");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void methodC(){
        try{
            lock.lock();
            System.out.println("ThreadName="+ Thread.currentThread().getName()+" method C start");
            Thread.sleep(1000);
            System.out.println("ThreadName="+ Thread.currentThread().getName()+" method C end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 使用Condition等待/通知
     * Condition对象：可以唤醒部分指定线程，有助于提升程序的运行效率
     * 可以对线程进行分组，然后唤醒指定组中的线程
     */
    private Condition condition = lock.newCondition();
    private Condition conA = lock.newCondition();

    private void await(){
        try {
            lock.lock();
            System.out.println("ThreadName="+ Thread.currentThread().getName()+" await() start");
            condition.await();
            System.out.println("ThreadName="+ Thread.currentThread().getName()+" await() end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void awaitA(){
        try {
            lock.lock();
            System.out.println("ThreadName="+ Thread.currentThread().getName()+" awaitA() start");
            conA.await();
            System.out.println("ThreadName="+ Thread.currentThread().getName()+" awaitA() end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void signal(){
        try {
            lock.lock();
            System.out.println(" signal() :"+System.currentTimeMillis());
//            condition.signal();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private void signalA(){
        try {
            lock.lock();
            System.out.println(" signalA() :"+System.currentTimeMillis());
            conA.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
       // methodA();
       // methodB();
       // methodC();
        await();
        awaitA();
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock01 rl01 = new ReentrantLock01();
        new Thread(rl01).start();
        new Thread(rl01).start();

        Thread.sleep(5000);
        rl01.signal();
        Thread.sleep(5000);
        rl01.signalA();
    }
}
