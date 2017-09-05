package com.gijon.concurrent;

/**
 * 生产者/消费者模式
 */
public class Thread23 {
    public static void main(String[] args) throws InterruptedException {
        String lock = new String("");
        P p = new P(lock);
        C c = new C(lock);

        /**
         * 生产者/消费者模式 1:1
         */
//        ThreadP tp = new ThreadP(p);
//        ThreadC tc = new ThreadC(c);
//        tp.start();
//        tc.start();

        /**
         * 生产者/消费者模式 n:n
         */
        ThreadP[] threadPS = new ThreadP[2];
        ThreadC[] threadCS = new ThreadC[2];
        for(int i = 0;i < 2; i++){
            threadPS[i] = new ThreadP(p);
            threadPS[i].setName("product"+i);
            threadCS[i] = new ThreadC(c);
            threadCS[i].setName("consumer"+i);
            threadPS[i].start();
            threadCS[i].start();
        }
        Thread.sleep(10000);
        Thread[] ts = new Thread[Thread.currentThread().getThreadGroup().activeCount()];
        Thread.currentThread().getThreadGroup().enumerate(ts);
        for (int i = 0; i < ts.length; i++){
            System.out.println(ts[i].getName()+"@@@@@@"+ ts[i].getState());
        }

    }
    /**
     *  wait条件改变：使用while替代if判断条件
     *
     *  假死：使用notifyAll替代notify
     */
}

/**
 * 生产者
 */
class P{
    private String lock;

    public P(String lock) {
        this.lock = lock;
    }

    public void setVal(){
        try {
            synchronized (lock) {
                if (!ValueObject.value.equals("")) {
                    System.out.println(Thread.currentThread().getName()+">>P>>>WAIT");
                    lock.wait();
                }
                System.out.println(Thread.currentThread().getName()+">>P>>>RUNNABLE");
                String val = System.currentTimeMillis()+"_"+System.nanoTime();
                ValueObject.value = val;
                //System.out.println("P>>>SET>>>"+val);
//                lock.notify();
                lock.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/**
 * 消费者
 */
class C{
    private String lock;

    public C(String lock) {
        this.lock = lock;
    }

    public void getVal(){
        try {
            synchronized (lock){
                if (ValueObject.value.equals("")){
                    System.out.println(Thread.currentThread().getName()+">>C>>>WAIT");
                    lock.wait();
                }
                System.out.println(Thread.currentThread().getName()+">>C>>>RUNNABLE");
                //System.out.println("C>>>GET>>>"+ValueObject.value);
                ValueObject.value = "";
//                lock.notify();
                lock.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadP extends Thread{
    private P p;

    public ThreadP(P p) {
        super();
        this.p = p;
    }

    @Override
    public void run() {
        while (true){
            p.setVal();
        }
    }
}

class ThreadC extends Thread{
    private C c;

    public ThreadC(C c) {
        super();
        this.c = c;
    }

    @Override
    public void run() {
        while (true){
            c.getVal();
        }
    }
}