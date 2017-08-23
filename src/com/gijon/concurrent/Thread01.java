package com.gijon.concurrent;

import java.util.Random;

public class Thread01 extends Thread{
    @Override
    public void run() {
        super.run();
        syso();
    }

    public static void main(String[] args) {
        Thread01 t1 = new Thread01();
        t1.start();
        syso();
    }

    public static void syso(){
        try {
            for (int i = 0; i < 10; i++){
                Thread.sleep(new Random().nextInt(1000));
                System.out.println(Thread.currentThread().getName()+" "+ i + " >> run()");
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
