package com.nkang.kxmoment.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * author  jin-zhou.zhang@hp.com
 */


public final class StringUtils {
    
    private StringUtils() {}

	//clear null value
	public static String clearNull(String temp){
		return temp.replace(" ","");	
	}
	
    public static Boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }
    
    public static Date dateConvertion(Date date) {
        Date result = null;
        String dateFormat = "yyyy/MM/dd HH:mm:ss";
        if (date != null) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                String tmp = simpleDateFormat.format(date);
                result = simpleDateFormat.parse(tmp);
            } catch (Exception e) {
            }
        }
        return result;
    }
    
    public static Boolean isEqual(String s,String s1) {
        
        return ((StringUtils.isEmpty(s) && StringUtils.isEmpty(s1)) || (!StringUtils.isEmpty(s) && s.equals(s1)));
    }
    
	
}
