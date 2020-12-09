package com.ruoyi.common.utils.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @Desc String 转换工具类
 * @Author
 * @Date:
 */
public class StringUtils {

    /**
     * 是否是数值
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
    	if(isFullNull(str)){
    		return false;
    	}
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    // 将字Clob转成String类型
    public static String ClobToString(Clob sc) throws SQLException, IOException {
        String reString = "";
        Reader is = sc.getCharacterStream();// 得到流
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            s = br.readLine();
        }
        reString = sb.toString();
        return reString;
    }
    /**
     * 判断传入的字符串如果为null则返回传入的默认值,否则返回其本身
     *
     * @param string 待判断字符串
     * @param instant 为空返回的默认值
     * @return String
     */
    public static String convertNull(String string, String instant) {
        return isFullNull(string) ? instant : string;
    }

    /**
     * 判断传入的字符串如果为null则返回"",否则返回其本身
     *
     * @param string 待判断字符串
     * @return String
     */
    public static String convertNull(String string) {
        return convertNull(string, "");
    }

    /**
     * 判断传入的参数是否为空
     * @param str
     * @return
     */
    public static boolean isFullNull(String str) {
        if (str == null || "".equals(str.trim()) || "null".equals(str) || "(null)".equals(str)) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @功能描述：替换方法
     * @参数说明：@param str
     * @参数说明：@param oldChar
     * @参数说明：@param newChar
     * @参数说明：@return 
     * @作者： ZQD
     * @创建时间：2017-11-7 上午9:09:16
     */
    public static String replaceStr(String str,String oldChar,String newChar){
        if(isFullNull(str)){
            return "";
        }else{
           return  str.replace(oldChar, newChar);
        }
    }

    /**
     * 判断对象是否为空
     *
     * @param obj Object
     * @return boolean 空返回true,非空返回false
     */
    public static boolean isNull(Object obj) {
        return (null == obj) ? true : false;
    }

	/**
	 * 截取字符串长度
	 * @param str
	 * @param length
	 * @return
	 */
	public static String limitLenth(String str, int length) {
		if (!isFullNull(str)) {
			byte[] b;
			try {
				b = str.getBytes("UTF-8");
				int res = b.length;
				if (res > length) {
					return new String(b, 0, length - 1);
				} else {
					return str;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
     * 适用于服务端对客户端版本判断
     * 把版本号字符串，按照.分隔后，当作数字比较 
     * add by hty 2017-12-14 上午9:07:21
     * @param str1
     *            前一个版本
     * @param str2
     *            后一个版本
     * @return 小于0,前者小于后者 ; 0:相等 ;大于0,前者大于后者
     * 
     * @author NZF 2014年12月9日
     */
    public static int compareTwoStr(String str1, String str2) {
        str1 = str1 == null ? "" : str1;
        str2 = str2 == null ? "" : str2;
        if (isFullNull(str1) && !isFullNull(str2)) {
            return -1;
        } else if (!isFullNull(str1) && isFullNull(str2)) {
            return 1;
        } else if (str1.equals(str2)) {
            return 0;
        } else if (str1.contains(".") && str2.contains(".")) {
            // 把版本号字符串，按照.分隔后，当作数字比较 add by hty 2017-12-14 上午9:07:21
            String[] strArr1 = str1.split("\\.");
            String[] strArr2 = str2.split("\\.");
            for (int i = 0; i < 3; i++) {
                if (null != strArr1 && strArr1.length > i && null != strArr2 && strArr2.length > i) {
                    int a = Integer.parseInt(strArr1[i] + "");
                    int b = Integer.parseInt(strArr2[i] + "");
                    if (a > b) {
                        return 1;
                    } else if (a < b) {
                        return -1;
                    }
                }
            }
            return 0;
        }else{
            return str1.compareTo(str2);
        }
    }

    /**
     * 适用于服务端判断版本升级,比较 一个数是否在区间返回内
     * 
     * @param str1
     *            需比较的参数,不能为空,如果为空,则默认返回不再区间内
     * @param str2
     *            区间下限 
     * @param str3
     *            区间上限
     * @return  1:无需升级(等于区间上限值);2:建议升级(不包括区间的两个极限值);3:强制升级(包括区间下限值)
     * 
     * @author NZF 2014年12月9日
     */
    public static int compareRangeStr(String str1, String str2, String str3) {
        str1 = str1 == null ? "" : str1;
        str2 = str2 == null ? "" : str2;
        str3 = str3 == null ? "" : str3;
        //当前版本号如果为空,即str1为空,则默认无需升级
        if (isFullNull(str1)) {
            return 1;
        }else{
            //当前版本大于等于区间上限,则是无需升级
            if(compareTwoStr(str1,str3)>=0){
                return 1;
            }
            //当前版本小于等于区间下限,则是强制升级
            if(compareTwoStr(str1,str2)<=0){
                return 3;
            }
            //其他情况都是建议升级
            return 2;
        }
    }
	public static String getSDFDate(String str){
	    if(isFullNull(str)){
	        return str;
	    }
	    if(str.length() == 8){
	        str = str.substring(0,4)+"年"+str.substring(4,6)+"月"+str.substring(6)+"日";
	    }
	    return str;
	}
}
