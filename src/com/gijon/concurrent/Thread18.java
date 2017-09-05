package com.gijon.concurrent;

public class Thread18 extends Thread {

    private MyService service;

    public MyService getService() {
        return service;
    }

    public void setService(MyService service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.testMethod();
    }

    public static void main(String[] args) throws InterruptedException {
        MyService myService = new MyService();
        Thread18 t181 = new Thread18();
        t181.setName("t181");
        t181.setService(myService);
        Thread18 t182 = new Thread18();
        t182.setName("t182");
        t182.setService(myService);

        t181.start();
        // lock = "456"; // 第二个线程延迟启动  +  字符串常量池--》导致Lock不是同一个对象
        // Thread.sleep(50); // 注释后抢夺的锁是"123"  只要对象不变，即使对象的属性改变，运行结果还是同步的
        t182.start();
    }
}

class MyService {
    private String lock = "123";
    public void testMethod(){
        try{
            synchronized (lock){
                System.out.println(Thread.currentThread().getName() + "begin "+ System.currentTimeMillis());
                lock = "456"; // 第二个线程延迟启动  +  字符串常量池--》导致Lock不是同一个对象
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "end "+ System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
