package com.gijon.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 类似第三章
 * 条件判断用while
 * 唤醒通知 signalAll
 *
 * 锁 Lock分为“公平锁”和“非公平锁”
 * 公平锁：线程获取锁的顺序是按照线程加锁的顺序来分配，即FIFO
 * 非公平锁：一种获取锁的抢占机制
 *
 * 线程interrupt() condition.awaitUninterruptily 不会抛异常
 */
public class ReentrantLock02 extends Thread {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private boolean hasVal = true;

    private ReentrantLock unfairLock = new ReentrantLock(false);

    private void set(){
        try {
            lock.lock();
            while (hasVal) {
                System.out.println("❤❤❤ "+lock.getHoldCount()+"<< hold count");
                condition.await();
            }
            hasVal = true;
            //System.out.println("❤");
            //condition.signal();
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void get(){
        try {
            lock.lock();
            while (!hasVal) {
                System.out.println("🚹🚹🚹 "+lock.getHoldCount()+"<< hold count");
                condition.await();
            }
            hasVal = false;
            //System.out.println("🚹");
            //condition.signal();
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        for (;;){
            get();
            set();
        }
    }

    public static void main(String[] args) {
        ReentrantLock02 lock2 = new ReentrantLock02();
        new Thread(lock2).start();
        new Thread(lock2).start();
        new Thread(lock2).start();
        new Thread(lock2).start();
        new Thread(lock2).start();
        new Thread(lock2).start();
    }
}
