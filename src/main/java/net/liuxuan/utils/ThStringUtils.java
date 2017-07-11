package net.liuxuan.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.utils.ThStringUtils
 * 功能: thymeleaf EL 调用。处理字符串
 * 版本:	@version 1.0
 * 编制日期: 2017/6/29 9:30
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/6/29  |    Moses       |     Created
 */
public class ThStringUtils {
    //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
    private static String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

    //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
    private static String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

    // 定义HTML标签的正则表达式
    private static String regEx_html = "<[^>]+>";

    private static Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
    private static Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
    private static Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);

    public static String removeHtmlTag(String instr) {
        StringBuilder sb = new StringBuilder();
        if (instr == null)
            return null;
        String htmlStr = instr; // 含html标签的字符串
        String textStr = "";

        Matcher m_script, m_style, m_html;

        try {
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;// 返回文本字符串
    }


    public static String limitStringLen(String str,int len){
        if (str==null) {
              return null;
        }
        len = len> str.length()? str.length():len;

        return str.substring(0,len);

    }

    public static String removeTagAndlimitStringLen(String str,int len){
        return limitStringLen(removeHtmlTag(str),len);

    }


}
