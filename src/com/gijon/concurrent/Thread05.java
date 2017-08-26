package com.gijon.concurrent;

/**
 * daemon
 */
public class Thread05 extends Thread {
    private int i = 0;
    public Thread05(String name) {
        super(name);
        this.setName(name);
    }

    @Override
    public void run() {
        try {
            while(true) {
                i++;
                System.out.println(Thread.currentThread().getName()+" i = " + i);
                Thread.sleep(1000);
                if(!Thread.currentThread().isDaemon() && i > 10) return;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try{
            Thread05 t051 = new Thread05("daemon thread 01");
            t051.setDaemon(true); // 进程中有非守护线程存在，则守护线程继续；无非守护线程存在，则守护线程自动销毁
            t051.start();
            Thread05 t052 = new Thread05("normal thread");
            t052.setDaemon(false);
            t052.start();
            Thread05 t053 = new Thread05("daemon thread 02");
            t053.setDaemon(true);
            t053.start();
            Thread.sleep(5000);
            System.out.println("我不玩了");
        }catch (InterruptedException e){

        }
    }
}
