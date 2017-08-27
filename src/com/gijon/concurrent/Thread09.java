package com.gijon.concurrent;

/**
 * 抛异常 中断线程
 */
public class Thread09 extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 500000; i++){
                if(this.isInterrupted())
                    throw new InterruptedException();
                System.out.println("i = "+ i);
            }
            System.out.println("after for still running ");
        } catch (InterruptedException e) {
            System.out.println("run catch exception ");
        }
    }

    public static void main(String[] args) {

        try {
            Thread09 t8 = new Thread09();
            t8.start();
            Thread.sleep(500);
            t8.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
