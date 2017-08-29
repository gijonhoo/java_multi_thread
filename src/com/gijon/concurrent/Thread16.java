package com.gijon.concurrent;

/**
 * 死锁
 * jps :查进程
 * jstack -l 进程编号 :查看死锁详情
 */
public class Thread16 extends Thread {
    private int flag;
    public Object lock1 = new Object();
    public Object lock2 = new Object();

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if(flag == 1){
            synchronized (lock1){
                try {
                    System.out.println("lock1 > lock1 ");
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2){
                    System.out.println("lock1 > lock2 ");
                }
            }
        }
        if(flag == 2){
            synchronized (lock2){
                try {
                    System.out.println("lock2 > lock2 ");
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1){
                    System.out.println("lock2 > lock1 ");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread16 t16 = new Thread16();
        t16.setFlag(1);
        Thread t161 = new Thread(t16);
        t161.start();
        Thread.sleep(1000);
        t16.setFlag(2);
        Thread t162 = new Thread(t16);
        t162.start();
    }
}
