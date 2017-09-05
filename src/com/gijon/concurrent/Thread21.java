package com.gijon.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Thread21 extends Thread {
    @Override
    public void run() {
        super.run();
    }

    public static void main(String[] args) {
        Object lock = new Object();
        Thread211 t211 = new Thread211();
        t211.setLock(lock);
        t211.start();
        try {
            TimeUnit.MICROSECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread212 t212 = new Thread212();
        t212.setLock(lock);
        t212.start();
    }

}

class Thread211 extends Thread {
    private Object lock;

    public void setLock(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            try {
                if(MyList.getSize() != 5) {
                    System.out.println("MyList.getSize() != 5");
                    /**
                     * 在调用wait()之前，线程必须获得该对象的对象级别锁
                     * wait()方法要在同步方法或同步块中调用
                     * 执行wait()后当前线程释放锁
                     */
                    lock.wait();
                    System.out.println("MyList.getSize() == 5");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Thread212 extends Thread {
    private Object lock;

    public void setLock(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            for (int i = 0; i < 10; i++){
                MyList.add();
                if(MyList.getSize() == 5){
                    /**
                     *  notify()方法要在同步方法或同步块中调用
                     *  执行notify()后，当前线程不会立即释放对象锁，
                     *  要等到执行notify()的线程将程序执行完，即退出synchronized代码块后当前线程才释放对象锁
                     *  而呈现wait状态所在的线程才会获得该对象锁
                     *  多个线程等待该对象的对象锁，则由线程规划器随机挑选出其中一个呈wait状态的线程，
                     *  对其发出通知notify，并使它等待获取该对象的对象锁
                     */
                    lock.notify();
                    System.out.println("虎虎虎");
                }
                System.out.println("current list size = "+ MyList.getSize());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class MyList {
    private static List list = new ArrayList();
    public static void add(){
        list.add("1");
    }
    public static int getSize(){
        return list.size();
    }
}
/** 出现阻塞的情况，解决后可进入runnable状态
 * sleep()，主动放弃占用的处理器资源；超过休眠时间
 * 阻塞式IO方法，；方法返回前，该线程阻塞
 * 试图获得同步监视器，但该同步监视器被其他线程持有
 * wait()；其他线程发出notify
 * suspend()挂起；resume()恢复
 */


/**
 * Runnable
 * Running
 * Blocked
 * Destory
 *
 * 每个锁对象都有两个队列：一个是就绪队列；一个是阻塞队列
 *
 * wait()锁释放 notify()锁不释放
 */

