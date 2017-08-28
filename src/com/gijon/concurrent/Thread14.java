package com.gijon.concurrent;

import org.omg.PortableServer.THREAD_POLICY_ID;

/**
 * synchronized 同步语句块
 */
public class Thread14 extends Thread {
    @Override
    public void run() {
        super.run();
        // doLongTimeTask0();
        // doLongTimeTask1();
        doLongTimeTask2();
        doLongTimeTask3();
    }

    private String getData1;
    private String getData2;

    public synchronized void doLongTimeTask0(){
        try{
            System.out.println("begin task");
            Thread.sleep(3000);
            String a = "长时间任务处理后从远程返回值:"+Thread.currentThread().getName();
            String b = "长时间任务处理后从远程返回值:"+Thread.currentThread().getName();
            getData1 = a;
            getData2 = b;
            System.out.println(getData1);
            System.out.println(getData2);
            System.out.println("end task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doLongTimeTask1(){
        try{
            System.out.println("begin task");
            Thread.sleep(3000);
            String a = "长时间任务处理后从远程返回值:"+Thread.currentThread().getName();
            String b = "长时间任务处理后从远程返回值:"+Thread.currentThread().getName();
            synchronized (this){
                getData1 = a;
                getData2 = b;
            }
            System.out.println(getData1);
            System.out.println(getData2);
            System.out.println("end task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 同步测试
    public void doLongTimeTask2(){
        for(int i = 0; i < 100; i++){
            System.out.println("asynchronized method "+ Thread.currentThread().getName() + " i = "+ i);
        }
        synchronized (this){
            for(int i = 0; i < 100; i++){
                System.out.println("synchronized method "+ Thread.currentThread().getName() + " i = "+ i);
            }
        }
    }

    // 当一个线程访问object的一个synchronized(this) 代码块时，其他线程对同一个object的所有其他synchronized(this)同步代码块的访问将阻塞
    // 即: synchronized 使用的“对象监视器”是一个

    public void doLongTimeTask3(){
        synchronized (this){
            for(int i = 100; i < 200; i++){
                System.out.println("synchronized method "+ Thread.currentThread().getName() + " i = "+ i);
            }
        }
    }

    public static void main(String[] args) {
        Thread14 t14 = new Thread14();
        new Thread(t14).start();
        new Thread(t14).start();
        // synchronized(this) 锁定当前对象
        Thread14 t141 = new Thread14();
        new Thread(t141).start();
    }
}
