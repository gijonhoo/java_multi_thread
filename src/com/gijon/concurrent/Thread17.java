package com.gijon.concurrent;

public class Thread17 extends Thread {
    @Override
    public void run() {
        super.run();
    }

    public static void main(String[] args) {
        OuterClass t17 = new OuterClass();
        OuterClass.InnerClass ic = t17.new InnerClass();
    }


}

class OuterClass {
    /**
     * 内部类
     */
    public class InnerClass{

    }

    /**
     * 静态内部类
     */
    public static class StaticInnerClass{

    }
}
