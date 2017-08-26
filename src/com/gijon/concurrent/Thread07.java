package com.gijon.concurrent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * timer
 */
public class Thread07 extends Thread {
    private static Timer timer = new Timer(); // 1. 启动新线程，非守护线程，一直运行
    //private static Timer timer = new Timer(true); // 2. 守护线程，计划时间未到而进程结束则不执行任务
    public static class MyTask0 extends TimerTask {
        private String name;
        private int round;

        public MyTask0(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            round++;
            System.out.println(name+ " begin ==> "+ new Date().toLocaleString());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name+ " end ==> "+ new Date().toLocaleString());
            if(round > 3){
                this.cancel(); // 6.将自身从任务队列中移除，其他任务不受影响
                System.out.println("round > 3 : this.cancel()");
            }
        }
    }

    public static class MyTask1 extends TimerTask {
        private int round;

        @Override
        public void run() {
            System.out.println("1 "+round +" begin ==> "+new Date().toLocaleString());
            try {
                Thread.sleep(1000);  // 任务不延时
                // Thread.sleep(3000); // 任务延时
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("1 "+round +" end ==> "+new Date().toLocaleString());
            if(++round > 10){
                // timer.cancel(); // 7. 清除全部任务，销毁进程
                // System.out.println("round > 10 : timer.cancel()");
            }
        }
    }

    public static void main(String[] args) throws ParseException {
        MyTask0 task = new MyTask0("任务0-1");
        MyTask0 task0 = new MyTask0("任务0-2");
        MyTask1 task1 = new MyTask1();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = "2017-08-26 23:30:00";
        Date dateRef = sdf.parse(s);
        System.out.println("字符串时间："+dateRef.toLocaleString()+" 当前时间： "+ new Date().toLocaleString());
        // timer.schedule(task, dateRef); //3. 执行一次；计划时间早于当前时间，立即执行
        // timer.schedule(task0, dateRef);
        // timer.schedule(task1, dateRef);

        // timer.schedule(task, dateRef, 10000);// 4.计划时间晚于当前时间：未来执行;早于当前时间：立即执行
        // timer.schedule(task, dateRef, 5000); // 5.任务被延时：顺序执行
        // timer.schedule(task1, dateRef, 2000);
        // timer.cancel(); // 8. 有时不会立即执行：cancel()没有抢到queue锁，TimerTask类中的任务继续正常执行

        // timer.schedule(task, 1000); //9. 延迟启动，执行一次
        // timer.schedule(task, 1000, 2000); // 10. 延迟启动，周期执行

        // schedule():如果任务没有被延时，那么下一次任务的执行时间参考的是上一次任务的“开始时间”来计算；
        // scheduleAtFixRate():如果任务没有被延时，下一次任务的执行时间参考上一次任务的“结束时间”来计算；
        // 任务延时：下一次任务的执行时间参考上一次任务的“结束时间”来计算；
        // timer.schedule(task1, 1000, 2000); // 11. 逾期任务不执行：任务不追赶
        // timer.scheduleAtFixedRate(task1, 5000, 2000); // 12. 任务没有被延时，下一次任务的时间是上一次任务开始的时间+delay的时间；
        timer.scheduleAtFixedRate(task1, dateRef, 2000); // 13. 逾期任务执行： 任务“补充性”执行(追赶)
    }
}
