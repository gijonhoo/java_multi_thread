package com.gijon.exception;

public class ThreadException {
    public static void main(String[] args) {
//        run1();
//        run2();
//        run3();
//        run4();
        run5();
    }

    private static void run1(){ // 对象
        MyThread t1 = new MyThread();
        t1.setUncaughtExceptionHandler(new ObjectUncaughtExceptionHandler());
        MyThread.setDefaultUncaughtExceptionHandler(new StateUncaughtExceptionHandler());
        t1.start();
    }

    private static void run2(){ // 类
        MyThread t1 = new MyThread();
        MyThread.setDefaultUncaughtExceptionHandler(new StateUncaughtExceptionHandler());
        t1.start();
    }

    private static void run3(){ // 对象
        MyThreadGroup tg = new MyThreadGroup("<<<线程组>>>");
        MyThread t1 = new MyThread(tg,"<线程>");
        t1.setUncaughtExceptionHandler(new ObjectUncaughtExceptionHandler());
        MyThread.setDefaultUncaughtExceptionHandler(new StateUncaughtExceptionHandler());
        t1.start();
    }

    private static void run4(){ // 类 + 线程组
        MyThreadGroup tg = new MyThreadGroup("<<<线程组>>>");
        MyThread t1 = new MyThread(tg,"<线程>");
        MyThread.setDefaultUncaughtExceptionHandler(new StateUncaughtExceptionHandler());
        t1.start();
    }

    private static void run5(){ // 对象 + 线程组
        MyThreadGroup tg = new MyThreadGroup("<<<线程组>>>");
        MyThread t1 = new MyThread(tg,"<线程>");
        t1.start();
    }
}

class ObjectUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("对象的异常处理");
        e.printStackTrace();
    }
}

class StateUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("类静态的异常处理");
        e.printStackTrace();
    }
}

class MyThreadGroup extends ThreadGroup {

    public MyThreadGroup(String name) {
        super(name);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        super.uncaughtException(t, e);
        System.out.println("线程组异常处理");
        e.printStackTrace();
    }
}

class MyThread extends Thread {
    private String num = "A";

    public MyThread() {
    }

    public MyThread(ThreadGroup group, String name) {
        super(group, name);
    }

    @Override
    public void run() {
        int numInt = Integer.parseInt(num);
        System.out.println(numInt+1);
    }
}
