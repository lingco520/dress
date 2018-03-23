package com.daqsoft.redis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Title: 线程池管理类
 * 本系统所有线程必须调用此类创建实例，避免线程混乱。
 * 注意事项：凡调用了线程的地方，记得用完线程后shutdown!!!
 * @Author:zf
 * @Date: 2017/08/23 9:55
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
public class ExecutorServiceUtils {

    //线程实例
    private static ExecutorService es ;

    //获取当前系统的CPU 数目
    private static int cpuNums = Runtime.getRuntime().availableProcessors();

    //每个CPU执行的最大线程数
    private static int pool_size=50;

    //记录线程未关闭，检测频率
    private static int executor_count=0;



    /***
     * 创建定长型线程池，可控制线程最大并发数，超出的线程会在队列中等待。
     * （暂时没发现弊端，推荐使用）
     * @return
     * @author  zf
     */
    public static ExecutorService getFixedThreadPool(){
        if(es!=null){
            //如果线程池已关闭，就创建新实例，反之返回现有的线程池；
            if (es.isShutdown()){
                es=Executors.newFixedThreadPool(cpuNums * pool_size);
            }
        }else {
            es=Executors.newFixedThreadPool(cpuNums * pool_size);
        }
        return es;
    }

    /***
     * 创建缓存型线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     * (弊端：会大量并发，如果遇到某线程周期较长，会无限制增涨直至卡崩)
     * 缓存型池子通常用于执行一些生存期很短的异步型任务,60s自动终止；
     * @return
     * @author  zf
     */
    public static ExecutorService getCachedThreadPool(){
        if(es!=null){
            //如果线程池已关闭，就创建新实例，反之返回现有的线程池；
            if (es.isShutdown()){
                es=Executors.newCachedThreadPool();
            }
        }else {
            es=Executors.newCachedThreadPool();
        }
        return es;
    }

    /***
     * 创建定长型线程池，支持定时及周期性任务执行 （定时执行）
     * @return
     * @author zf
     */
    public static ExecutorService getScheduledThreadPool(){
        if(es!=null){
            //如果线程池已关闭，就创建新实例，反之返回现有的线程池；
            if (es.isShutdown()){
                es=Executors.newScheduledThreadPool(cpuNums * pool_size);
            }
        }else {
            es=Executors.newScheduledThreadPool(cpuNums * pool_size);
        }
        return es;
    }

    /***
     * 创建单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
     * @return
     * @author zf
     */
    public static ExecutorService getSingleThreadExecutor(){
        if(es!=null){
            //如果线程池已关闭，就创建新实例，反之返回现有的线程池；
            if (es.isShutdown()){
                es=Executors.newSingleThreadExecutor();
            }
        }else {
            es=Executors.newSingleThreadExecutor();
        }
        return es;
    }


    /***
     * 监测线程池状态的定时线程，每分钟监测一次
     */
    public static void runScheduledThread(){

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                if (es!=null){
                    if (!es.isShutdown()){
                        executor_count++;
                        System.out.println("提示：【线程池未关闭，正在运行。】");
                        //超过5分钟，强制关闭线程池
                        if(executor_count>=5){
                            es.shutdownNow();
                            System.out.println("提示：线程超时5分钟，执行强制关闭！");
                        }
                    }else{
                        executor_count=0;
                        System.out.println("提示：【线程池已关闭，停止运行。】");
                    }
                }
            }
        }, 120, 60, TimeUnit.SECONDS);
    }


    public static void runScheduledState(){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                if (es!=null){
                    if (!es.isShutdown()){
                        System.out.println("提示：【线程池未关闭，正在运行。】");
                    }else{
                        System.out.println("提示：【线程池已关闭，停止运行。】");
                    }
                }
            }
        }, 120, 60, TimeUnit.SECONDS);
    }





}