package com.gijon.concurrent;

import java.util.concurrent.atomic.AtomicLong;

public class Thread20 implements Runnable {
    private NumService service;
    private ValService valService;
    public NumService getService() {
        return service;
    }

    public void setService(NumService service) {
        this.service = service;
    }

    public void setValService(ValService valService) {
        this.valService = valService;
    }

    @Override
    public void run() {
        if(service != null)
        service.addNum();
        if(valService != null)
        valService.startService();
    }

    public static void main(String[] args) throws InterruptedException {
        NumService numService = new NumService();
        Thread[] thread20s = new Thread[20];
        for (int i = 0; i < 20; i++) {
            Thread20 t = new Thread20();
            t.setService(numService);
            thread20s[i] = new Thread(t);
        }
        for (Thread t20:thread20s) {
            t20.start();
        }

        ValService valService = new ValService();
        Thread20 t200 = new Thread20();
        t200.setValService(valService);
        new Thread(t200).start();
        Thread.sleep(3000);
        valService.stopServie();
    }
}

class NumService {
    /**
     * 原子类在具有有逻辑性的情况下输出结果也具有随机性
     * 原因：addAndGet() 方法是原子的，但方法和方法之间的调用却不是原子的
     * 解决办法：必须使用同步 addNum() 添加 synchronized修饰
     */
    public static AtomicLong  atomicLong = new AtomicLong();
    public synchronized void addNum(){
        System.out.println(Thread.currentThread().getName() + "加了100之后的值："+ atomicLong.addAndGet(100));
        atomicLong.addAndGet(1);
    }
}

class ValService {
    private boolean isContinue = true;
    public void startService(){
        String s = new String();
        while(isContinue){
            synchronized (s){
                /**
                 *  synchronized 可以保证在同一时刻，只有一个线程可以执行某一个方法或某一个代码块
                 *  包含 互斥性 和 可见性
                 *  外练互斥 内修可见
                 */
            }
        }
        System.out.println("停下来了");
    }

    public void stopServie(){
        isContinue = false;
    }
}


