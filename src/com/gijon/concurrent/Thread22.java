package com.gijon.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * wait()锁释放 notify()锁不释放:执行完方法所在synchronized代码块后才释放锁
 * 当线程处于wait状态时，调用线程对象的interrupt()出现InterruptedException
 */
public class Thread22 {

    public static void main(String[] args) throws InterruptedException {
        String lock = new String("");
        Add add = new Add(lock);
        Minus minus = new Minus(lock);
        Thread222 t2221 = new Thread222();
        t2221.setP(minus);
        t2221.setName("2221");
        t2221.start();
        Thread222 t2222 = new Thread222();
        t2222.setP(minus);
        t2222.setName("2222");
        t2222.start();
        Thread.sleep(1000);
        Thread221 thread221 = new Thread221();
        thread221.setP(add);
        thread221.setName("221");
        thread221.start();
    }
}

class Add{
    private String lock;
    public Add(String lock){
        this.lock = lock;
    }

    public void add(){
        synchronized (lock){
            ValueObject.list.add("anything");
            //lock.notify();  // 一个线程继续阻塞
            lock.notifyAll(); // 一个线程异常退出
        }
    }
}

class Minus{
    private String lock;
    public Minus(String lock){
        this.lock = lock;
    }

    public void minus(){
        synchronized (lock){
            try {
//                if(ValueObject.list.size() == 0) {  // old
                while(ValueObject.list.size() == 0) { // new
                    System.out.println("wait begin " + Thread.currentThread().getName());
                    lock.wait();
                    System.out.println("wait end" + Thread.currentThread().getName());
                }
                ValueObject.list.remove(0);
                System.out.println("list's size =" +ValueObject.list.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ValueObject{
    public static List<String> list = new ArrayList();

    public static String value = "";

}

class Thread221 extends Thread{
    private Add p;

    public void setP(Add p) {
        this.p = p;
    }

    @Override
    public void run() {
        super.run();
        p.add();
    }
}


class Thread222 extends Thread{
    private Minus p;

    public void setP(Minus p) {
        this.p = p;
    }

    @Override
    public void run() {
        super.run();
        p.minus();
    }
}
