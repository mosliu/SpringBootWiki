package test;

import java.io.*;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  test.parse
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/19 10:40
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/19 |    Moses       |     Created
 */
public class parse {
    public static void main(String[] args) throws IOException {
//        System.out.println(1);
        String encoding = "GBK";
        File f = new File("d:/c.txt");
        InputStreamReader read = new InputStreamReader(
                new FileInputStream(f), encoding);//考虑到编码格式
        BufferedReader br = new BufferedReader(read);
        String lineTxt = null;
        while ((lineTxt = br.readLine()) != null) {
            parseLine(lineTxt);
        }
    }

    public static void parseLine(String lineTxt) {
        if (
                lineTxt.startsWith("    ├─") || lineTxt.startsWith("    └─")
                //||lineTxt.startsWith("│  │  ├─") || lineTxt.startsWith("│  │  └─")
                ) {

            System.out.println(lineTxt);

        }
    }
}
