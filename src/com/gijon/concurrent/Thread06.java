package com.gijon.concurrent;

import java.io.*;
/**
 * single
 */
public class Thread06 extends Thread {
    @Override
    public void run() {
        System.out.println("0 double-check locking: "+MyObject0.getInstance().hashCode());
        System.out.println("1 static inner class:   "+MyObject1.getInstance().hashCode());
        System.out.println("3 static{}:             "+MyObject3.getInstance().hashCode());
        System.out.println("4 enum:                 "+MyEnum.objectFactory.getObject4().hashCode());
        System.out.println("5 enum objectFactory:   "+MyObject5.getFromObjectFactory().hashCode());
        System.out.println("5 enum classFactory:    "+MyObject5.getFromClassFactory().hashCode());
    }

    public static void main(String[] args) {
        Thread06 t61 = new Thread06();
        Thread06 t62 = new Thread06();
        Thread06 t63 = new Thread06();
        t61.start();
        t62.start();
        t63.start();

        try {
            MyObject2 obj2 = MyObject2.getInstance();
            FileOutputStream fosRef = new FileOutputStream(new File("myObjectFile.txt"));
            ObjectOutputStream oosRef = new ObjectOutputStream(fosRef);
            oosRef.writeObject(obj2);
            oosRef.close();
            fosRef.close();
            System.out.println(obj2.hashCode());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            FileInputStream fisRef = new FileInputStream(new File("myObjectFile.txt"));
            ObjectInputStream oisRef = new ObjectInputStream(fisRef);
            MyObject2 object2 = (MyObject2) oisRef.readObject();
            oisRef.close();
            fisRef.close();
            System.out.println(object2.hashCode());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

// DCL double-check locking // 懒汉模式
class MyObject0{
    private MyObject0(){}

    public static MyObject0 obj;

    public static MyObject0 getInstance(){
        if(obj == null){
            try {
                Thread.sleep(3000);
                synchronized(MyObject0.class){
                    if(obj == null){
                        obj = new MyObject0();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
}

// 静态内置类
class MyObject1{
    private static class MyObjectHandler {
        private static MyObject1 object1 = new MyObject1();
    }

    private MyObject1(){}

    public static MyObject1 getInstance(){
        return MyObjectHandler.object1;
    }

}



// 序列化和反序列化 导致结果多例 解决方法如下：
/*
    protected Object read Resolve() throws ObjectStreamException{
        return MyObjectHandler.myObject;
    }
*/
class MyObject2 implements Serializable{
    private static final long serialVersionUID = 8888L;
    // 静态内部类
    private static class MyObjectHandler{
        private static final MyObject2 object2 = new MyObject2();
    }

    private MyObject2(){}

    public static MyObject2 getInstance() {
        return MyObjectHandler.object2;
    }

    protected Object readResolve() throws ObjectStreamException{
        System.out.println("-----------------------");
        return MyObjectHandler.object2;
    }
}

// 静态代码块 
class MyObject3 {
    private MyObject3(){}
    
    public static MyObject3 object3;
    
    static{
        object3 = new MyObject3();
    }

    public static MyObject3 getInstance() {
        return object3;
    }
}

// enum枚举数据类型：使用枚举类时构造方法会被自动调用
enum MyEnum {
    objectFactory;
    public MyObject4 object4;
    MyEnum(){
        object4 = new MyObject4();
    }
    public MyObject4 getObject4(){
        return object4;
    }

    class MyObject4 {
    }
}



// 前例违反单一职责原则 优化如下：
// 另： 一个枚举类型实例化一次构造方法
class MyObject5 {
    public enum MyEnumSingleton {
        objectFactory,
        classFactory;
        private MyObject5 object5;
        MyEnumSingleton() {
            object5 = new MyObject5();
        }

        public MyObject5 getObject5() {
            return object5;
        }
    }

    // 二者返回不同对象 欲单例则只能有一个枚举实例
    public static MyObject5 getFromObjectFactory(){
        return MyEnumSingleton.objectFactory.getObject5();
    }
    public static MyObject5 getFromClassFactory(){
        return MyEnumSingleton.classFactory.getObject5();
    }
}


