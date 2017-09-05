package com.gijon.concurrent;

/**
 * 静态synchronized方法 : synchronized关键字加到static方法上是给Class类加锁
 *                        synchronized关键字加到非static方法上是给对象加锁
 * synchronized(class)代码块
 */
public class Thread15 extends Thread {
    private int flag;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public synchronized static void printA(){
        try{
            System.out.println("线程名称："+Thread.currentThread().getName() +"begin "+ System.currentTimeMillis()+" print A");
            Thread.sleep(3000);
            System.out.println("线程名称："+Thread.currentThread().getName() +"end   "+ System.currentTimeMillis()+" print A");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void printB(){
        System.out.println("线程名称："+Thread.currentThread().getName() +"begin "+ System.currentTimeMillis()+" print B");
        System.out.println("线程名称："+Thread.currentThread().getName() +"end   "+ System.currentTimeMillis()+" print B");
    }

    /**
     * 相同对象 静态方法 同步执行 非静态方法与静态方法异步执行
     * 测试 Class锁 与 Object锁 不是同一个锁
     * 非静态方法 Object锁
     */
    public synchronized void printC(){
        System.out.println("线程名称："+Thread.currentThread().getName() +"begin "+ System.currentTimeMillis()+" print C");
        System.out.println("线程名称："+Thread.currentThread().getName() +"end   "+ System.currentTimeMillis()+" print C");
    }

    /**
     * Class锁可以对类的所有的对象实例起作用 ：不同对象 同步执行
     * synchronized static method() == synchronized(Class.class){}
     */

    @Override
    public void run() {
        if(flag == 1) printA();
        else if(flag == 2) printB();
        else printC();
    }

    public static void main(String[] args) {
        Thread15 t151 = new Thread15();
        Thread15 t152 = new Thread15();
        Thread15 t153 = new Thread15();

        t151.setFlag(1);
        t152.setFlag(2);
        t153.setFlag(3);
        t151.start();
        t152.start();
        t153.start();

    }

}
