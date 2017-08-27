package com.gijon.concurrent;

public class Thread02 extends Thread {
    private int count = 5;

    public Thread02(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        while(count > 0){
            count--;
            System.out.println(Thread.currentThread().getName()+" : "+count);
            System.out.println(this.currentThread().getName()+" : 休眠对象 ");
        }
    }

    public static void main(String[] args) {
        Thread02 t021 = new Thread02("t021");
        Thread02 t022 = new Thread02("t022");
        Thread02 t023 = new Thread02("t023");
        Thread02 t024 = new Thread02("t024");
        Thread02 t025 = new Thread02("t025");
        t021.start();
        t022.start();
        t023.start();
        t024.start();
        t025.start();
    }
}
