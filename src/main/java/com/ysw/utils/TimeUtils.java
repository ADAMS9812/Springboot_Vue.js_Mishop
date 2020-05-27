package com.ysw.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具
 */

public class TimeUtils {

    /**
     * 获取当前时间的方法
     */
    public static String getTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(date);
        //根据时间工程格式返回现在的时间
        return currentTime;
    }

    /**
     * 生成一个订单号
     */
    public static String getOrderNumber(){
        String msg = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");
        msg = msg + sdf.format(date);
        //生成一个订单号
        return msg;
    }

}
