package com.ruoyi.project.need.utils;

import com.ruoyi.common.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 封装工具类
 */
public class util {
    //修改时间的格式
    public static String strToDateFormat(String date) throws Exception {
        String str="";
        String reg = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
        if(!StringUtils.isEmpty(date)){

             str= date.replaceAll(reg, "$1-$2-$3 $4:$5:$6");

        }
        return str;


    }

}

