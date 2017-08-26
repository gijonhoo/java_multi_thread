package com.gijon.concurrent;

/**
 * priority
 */
public class Thread04 extends Thread {

    public Thread04(String name) {
        super(name);
        this.setName(name);
    }

    @Override
    public void run() {
        long beginTime = System.currentTimeMillis();
        int count = 0;
        for (int i = 0; i < 50000000; i++){
            //Thread.yield();
            Thread.yield();
            count = count + (i + 1);
        }
        long endTime = System.currentTimeMillis();
        System.out.print(Thread.currentThread().getName()+" 用时:"+ (endTime - beginTime)+ "毫秒！");
        System.out.println(Thread.currentThread().getName()+" priority : "+Thread.currentThread().getPriority());

        Thread03 t03 = new Thread03("测试线程优先级传递性");
        t03.start();
        System.out.println(Thread.currentThread().getName()+" t03的priority : "+t03.getPriority());
    }

    public static void main(String[] args) {
        System.out.println("main的priority : "+Thread.currentThread().getPriority());
        Thread.currentThread().setPriority(MIN_PRIORITY);
        Thread04 t041 = new Thread04("min priority");
        t041.start();
        Thread04 t04 = new Thread04("max priority");
        t04.setPriority(MAX_PRIORITY); // 线程优先级具有传递性
        t04.start();
    }
}
