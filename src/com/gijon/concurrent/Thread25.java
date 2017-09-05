package com.gijon.concurrent;

import java.util.Date;

/**
 * join : 在内部用wait
 * synchronized: 使用对象监视器
 *
 * join过程中，如果当前线程对象被中断，则当前线程出现异常
 *
 * join(long)与sleep(long)
 * join(long) 内部使用wait(long)实现，释放锁
 * sleep(long) 不释放锁
 *
 * ThreadLocal:解决的是变量在不同线程间的隔离性，
 * 也就是不同线程拥有自己的值，不同线程中的值是可以放入ThreadLocal类中进行保存的
 *
 * InheritableThreadLocal 子线程从父线程中取得值
 */
public class Thread25 {
    public static ThreadLocal tl = new ThreadLocal();
    public static ThreadLocal<Date> td = new ThreadLocal<>();

    public static void main(String[] args) {
        if(tl.get() == null){
            System.out.println("从未放过值");
            tl.set("=====================");
            td.set(new Date());
        }
        System.out.println(tl.get());
        System.out.println(tl.get());
        System.out.println(td.get());

        System.out.println(Tools.tl.get());

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread "+ Tools.tl.get());
            }
        }).start();
    }
}

class InheritableThreadLocalEx extends InheritableThreadLocal {
    @Override
    protected Object initialValue() {
        return new Date().getTime();
    }

    @Override
    protected Object childValue(Object parentValue) {
        return parentValue + " ===》 childValue";
    }
}

class Tools {
    public static InheritableThreadLocalEx tl = new InheritableThreadLocalEx();
}
