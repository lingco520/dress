package com.daqsoft.utils.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class EUtils {
	public static String spellParam(Map<String, String> params){
		String param = "";
		String key = "";
		String value = "";
		StringBuffer sb = null;  
		if (params != null) {  
			Iterator<String> it = params.keySet().iterator();  
			while (it.hasNext()) {  
				key = it.next();
				value = params.get(key);
				if (sb == null) {  
					sb = new StringBuffer();  
//					sb.append("?");  
				}else {  
					sb.append("&");  
				}  
			  	sb.append(key);  
			  	sb.append("=");  
			  	sb.append(value);  
			} 
			param = String.valueOf(sb);
		}
		return param;
	}
	
	public static boolean isNotEmpty(Object obj){
        String str = String.valueOf(obj);
        boolean flag = false;
        if(str != null && !"".equals(str.trim()) && !"null".equals(str.trim())){
            flag = true;
        }
        return flag;
    }

    public static boolean isEmpty(Object obj){
        String str = String.valueOf(obj);
        boolean flag = false;
        if(str == null || "".equals(str.trim()) || "null".equals(str.trim()) || "\"null\"".equals(str.trim())){
            flag = true;
        }
        return flag;
    }
    
    public static String getCurrentTime(){
    	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    
    public static String getCurrentDate(){
    	return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
    public static String getCurrentHour(){
    	return new SimpleDateFormat("HH").format(new Date());
    }

}
