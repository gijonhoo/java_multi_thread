package com.gijon.concurrent;

/**
 * interrupt() 中断线程
 */
public class Thread08 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 500000; i++){
            if(this.isInterrupted()) break;
            System.out.println("i = "+ i);
        }
        System.out.println("after for still running ");
    }

    public static void main(String[] args) {

        try {
            Thread08 t8 = new Thread08();
            t8.start();
            Thread.sleep(500);
            t8.interrupt();
            // System.out.println("是否停止1？ ="+ t8.interrupted()); // 1. 测试当前线程即main线程;清除状态标识
            // System.out.println("是否停止2？ ="+ t8.interrupted());

            System.out.println("是否停止3？ ="+ t8.isInterrupted()); // 2. 测试线程Thread对象是否已经是中断状态; 《？？？不清除状态标识》》》
            System.out.println("是否停止4？ ="+ t8.isInterrupted()); // 2. 测试线程Thread对象是否已经是中断状态

            // Thread.currentThread().interrupt();
            // System.out.println("是否停止5？ ="+ Thread.interrupted());
            // System.out.println("是否停止6？ ="+ Thread.interrupted());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
