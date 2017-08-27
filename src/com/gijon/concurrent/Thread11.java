package com.gijon.concurrent;

/**
 * 暂停线程
 */
public class Thread11 extends Thread {
    private long i;

    public long getI() {
        return i;
    }

    public void setI(long i) {
        this.i = i;
    }

    @Override
    public void run() {
        while(true) i++;
    }

    public static void main(String[] args) {
        try{
            Thread11 t11 = new Thread11();
            t11.start();
            Thread.sleep(3000);
            t11.suspend();
            System.out.println("A = "+System.currentTimeMillis() + " i = "+ t11.getI());
            Thread.sleep(3000);
            System.out.println("A = "+System.currentTimeMillis() + " i = "+ t11.getI());
            t11.resume();
            Thread.sleep(3000);
            System.out.println("B = "+System.currentTimeMillis() + " i = "+ t11.getI());
            t11.suspend();
            System.out.println("C = "+System.currentTimeMillis() + " i = "+ t11.getI());
            Thread.sleep(3000);
            System.out.println("C = "+System.currentTimeMillis() + " i = "+ t11.getI());
        }catch (Exception e){

        }
    }
}
