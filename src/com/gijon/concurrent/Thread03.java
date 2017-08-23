package com.gijon.concurrent;

public class Thread03 extends Thread {
    int count = 5;

    public Thread03(String name){
        this.setName(name);
    }
    @Override
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " : " + count);
    }

    public static void main(String[] args) {
        Thread03 t03 = new Thread03("t03");
        Thread t031 = new Thread(t03, "t031");
        Thread t032 = new Thread(t03, "t032");
        Thread t033 = new Thread(t03, "t033");
        Thread t034 = new Thread(t03, "t034");
        Thread t035 = new Thread(t03, "t035");
        t031.start();
        t032.start();
        t033.start();
        t034.start();
        t035.start();
    }
}
