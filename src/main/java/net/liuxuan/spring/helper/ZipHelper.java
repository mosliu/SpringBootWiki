package net.liuxuan.spring.helper;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.Helper.ZipHelper
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/1/6 15:44
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/1/6  |    Moses       |     Created
 */
public class ZipHelper {
    /**
     * 未处理文件夹
     * @param srcFiles 需压缩的文件路径及文件名
     * @param desFile  保存的文件名及路径
     * @return 如果压缩成功返回true
     */
    public static boolean zipCompress(String[] srcFiles, String desFile) {
        boolean isSuccessful = false;


        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desFile));
            ZipOutputStream zos = new ZipOutputStream(bos);
            String entryName = null;
            File tmpfile = null;
            for (int i = 0; i < srcFiles.length; i++) {
                tmpfile = new File(srcFiles[i]);
                entryName = tmpfile.getName();

                // 创建Zip条目
                ZipEntry entry = new ZipEntry(entryName);
                zos.putNextEntry(entry);

                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFiles[i]));

                byte[] b = new byte[1024];

                while (bis.read(b, 0, 1024) != -1) {
                    zos.write(b, 0, 1024);
                }
                bis.close();
                zos.closeEntry();
            }

            zos.flush();
            zos.close();
            isSuccessful = true;
        } catch (IOException e) {
        }

        return isSuccessful;
    }

    // 解析文件名
    private static String parse(String srcFile) {
        int location = srcFile.lastIndexOf("/");
        String fileName = srcFile.substring(location + 1);
        return fileName;
    }

    /*
     * @param srcZipFile 需解压的文件名
     * @return  如果解压成功返回true
     */
    public static boolean unzip(String srcZipFile) {
        boolean isSuccessful = true;
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcZipFile));
            ZipInputStream zis = new ZipInputStream(bis);

            BufferedOutputStream bos = null;

            //byte[] b = new byte[1024];
            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                bos = new BufferedOutputStream(new FileOutputStream("d:/" + entryName));
                int b = 0;
                while ((b = zis.read()) != -1) {
                    bos.write(b);
                }
                bos.flush();
                bos.close();
            }
            zis.close();
        } catch (IOException e) {
            isSuccessful = false;
        }
        return isSuccessful;
    }

}
