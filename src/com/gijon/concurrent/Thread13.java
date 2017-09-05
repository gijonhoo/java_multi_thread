package com.gijon.concurrent;

public class Thread13 extends Thread {
    private static PublicVar var;
    @Override
    public void run() {
        super.run();
        var.setVal("aaaaa", "ppppp");
    }

    public static void main(String[] args) throws InterruptedException {
        var = new PublicVar();
        Thread13 t13 = new Thread13();
        t13.start();
        Thread.sleep(200); // 影响结果
        var.getVal();
    }
}

class PublicVar {
    public String name = "A";
    public String password = "P";

    public synchronized void setVal(String name, String password){
        try{
            this.name = name;
            Thread.sleep(5000);
            this.password = password;
            System.out.println("setVal() thread name = "+ Thread.currentThread().getName() + " username = "+ name + " password = "+ password);

            // synchronized 锁重入：当一个线程得到一个对象锁后，再次请求此对象锁是可以再次得到该对象的锁
            // 即: 在一个 synchronized方法/块的内部调用本类的其他synchronized方法是永远可以得到锁的
            getVal();
            // 支持在父子类继承的环境中
            PublicSubVar subVar = new PublicSubVar();
            subVar.operateSub();
            // 可正常调用父类异步方法
            subVar.operateMainAsyn();
            // 异常自动释放锁

            // 同步不具有继承性

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    public void getVal(){
        System.out.println("getVal() thread name = "+ Thread.currentThread().getName() + " username = "+ name + " password = "+ password);
    }*/

    public synchronized void getVal(){
        System.out.println("getVal() thread name = "+ Thread.currentThread().getName() + " username = "+ name + " password = "+ password);
    }

    public int i = 100;

    public synchronized void operateMain(){
        try{
            i--;
            System.out.println("main print i = "+ i);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void operateMainAsyn(){
        try{
            i--;
            System.out.println("main asyn print i = "+ i);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class PublicSubVar extends  PublicVar{

    public synchronized void operateSub() {
        try{
            i--;
            System.out.println("sub print i = "+ i);
            Thread.sleep(100);
            this.operateMain();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



