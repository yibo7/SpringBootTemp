package com.ebsite.tempsite.utils;

/**
 * 计算执行时间
 *
 * @author 蔡齐盛
 * @create 2018-07-03 23:24
 **/
public class RunTimeEb {
    long startTime =0;
    private String sName = "";
    public RunTimeEb(){

    }
    public void start(String name){
        sName = name;
          startTime = System.currentTimeMillis();    //获取开始时间
    }
    public void end(){
        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println(sName+"运行时间：" + (endTime - startTime) + "毫秒");    //输出程序运行时间
    }
}
