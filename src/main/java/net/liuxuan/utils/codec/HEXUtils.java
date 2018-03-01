package net.liuxuan.utils.codec;

/**
 * Copyright (c) 2010-2018.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.utils.codec.HEXUtils
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2018/3/1 10:34
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2018/3/1  |    Moses       |     Created
 */
public class HEXUtils {
    /**
     * 把字节流转换为十六进制表示方式。
     */
    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}
