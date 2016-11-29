/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.utils.JsonUtils
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/11/15 14:25
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/11/15  |    Moses       |     Created
 */
package net.liuxuan.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Iterator;


public class JsonUtils {
    public static JsonArray stringArr2JSON(String[] s){
        JsonArray jarr = new JsonArray();
        for(int i=0;i<s.length;i++){
            JsonObject obj = new JsonObject();
            obj.addProperty(String.valueOf(i), s[0]);
            jarr.add(obj);
        }

        return jarr;
    }

    public static JsonObject stringHash2JSON(HashMap<String,String> hm){
        JsonObject jobj = new JsonObject();
        for(String key : hm.keySet()){
            jobj.addProperty(key, (String)hm.get(key));
        }
        return jobj;
    }

    /**
     * converts a hashmap to JSON
     * @param hm
     * @param truncate - in some cases, doubles can be truncated to int if they are something like 127.0 truncate == true casts them as integer values truncate == false leaves them as double
     * @return
     */
    public static String computeJSON(HashMap hm, boolean truncate){
        JsonObject f = new JsonObject();
        for (Iterator it = hm.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            Object value = hm.get(key);
            if(truncate){
                if(value.toString().endsWith(".0") && isDouble(value.toString()) ){
                    value = value.toString().replace(".0", "");
                }
            }
            if(isInt(value.toString())){
                f.addProperty(key, toInt(value.toString()));
            }else if(isDouble(value.toString())){
                f.addProperty(key, toDouble(value.toString()));
            }else {
                f.addProperty(key, value.toString());
            }
        }
        return f.toString().trim();
    }


    public static boolean isInt(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    public static int toInt(String s){
        int p = Integer.parseInt(s);
        return p;
    }

    public static boolean isDouble(String s){
        try{
            Double.parseDouble(s);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    public static double toDouble(String s){
        double p = Double.parseDouble(s);
        return p;
    }
}
