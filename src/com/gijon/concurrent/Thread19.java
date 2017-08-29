package com.gijon.concurrent;

/**
 * volatile 是变量在多个线程间可见
 */
public class Thread19 implements Runnable {
    //private boolean isContinuePrint = true;
    private volatile boolean isContinuePrint = true;

    public void setContinuePrint(boolean continuePrint) {
        isContinuePrint = continuePrint;
    }

    public void printMethod() throws InterruptedException {
        while(isContinuePrint){
            System.out.println(java.lang.Thread.currentThread().getName()+" --> thread name");
            java.lang.Thread.sleep(3000);
        }
    }



    @Override
    public void run() {
        try {
            printMethod();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread19 t20 = new Thread19();
        new java.lang.Thread(t20).start();
        java.lang.Thread.sleep(3000);
        t20.setContinuePrint(false);
        /**
         * 以上代码运行在 -server服务器模式中64bit的JVM上会出现死循环。
         * 原因：在JVM被设置为-server模式时为了线程的运行效率，线程一直在私有堆栈中取得变量值
         * 代码更新的是公共堆栈中的变量的值
         *
         *  解决办法：使用volatile关键字
         *  关键字volatile的作用是强制从公共堆栈中取得变量的值，而不是从线程私有数据栈中去的变量的值
         *
         *  缺点：不支持原子性
         *
         *  synchronized 和 volatile 比较：
         *  a. 关键字volatile是线程同步的轻量级实现，性能较好；只能修饰于变量。
         *     synchronized可以修饰方法以及代码块，新版本jdk中执行效率很大提升
         *  b. 多线程访问volatile不会阻塞，synchronized会阻塞
         *  c. volatile能保证数据的可见性，但不具备同步性，也就不能保证原子性；
         *     synchronized可以保证原子性也可以间接保证可见性，它将是私有内存和公共内存中的数据做同步。
         *  d. volatile 解决的是 变量在多个线程中的可见性
         *     synchronized 解决的是 多个线程之间访问资源的的同步性
         *
         *
         *  线程安全包含原子性和可见性两个方面。
         *
         */

    }
}
