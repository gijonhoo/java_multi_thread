package com.gijon.concurrent;

/**
 *  局部变量线程安全
 *  实例变量非线程安全
 *  多个对象多个锁
 */
public class Thread12 extends Thread {
    private String name;
    private MyObject obj;
    public Thread12(String name, MyObject obj) {
        this.name = name;
        this.obj  = obj;
    }

    @Override
    public void run() {
        super.run();
        obj.addI(name);
    }

    public static void main(String[] args) {
        MyObject obj = new MyObject();
        Thread12 t121 = new Thread12("a", obj);
        Thread12 t122 = new Thread12("b", obj);
        t121.start();
        t122.start();

        MyObject obj1 = new MyObject();
        Thread12 t123 = new Thread12("c", obj1);
        t123.start();
    }
}

class MyObject {
    private int num = 0;

    // synchronized 取得对象锁：哪个线程先执行带synchronized关键字的方法，哪个线程就持有该方法所属对象的锁Lock;
    // 其他线程只能等待，前提是多个线程访问的是同一个对象
    public synchronized void addI(String name){
        try{
            if(name.equals("a")){
                num = 100;
                System.out.println("a set over");
                Thread.sleep(3000);
            }else{
                num = 200;
                System.out.println(name + " set over");
            }
            System.out.println("name = "+ name + " num = "+ num);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
