package net.liuxuan.SprKi.controller.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.utils.FileUploadUtil
 * 功能: 配合Uploadifive
 * 版本:	@version 1.0
 * 编制日期: 2017/5/13 11:42
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/5/13  |    Moses       |     Created
 */
public class FileUploadUtil {
    private static Logger log =  LoggerFactory.getLogger(FileUploadUtil.class);

    public static List<String> uploadFile(HttpServletRequest request, String filePath, String filePathUrl) throws FileNotFoundException {
        List<String> filePathList = new ArrayList<String>();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        String fileName = null;
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {

            MultipartFile mf = entity.getValue();
            fileName = mf.getOriginalFilename();
            String newfilepath;
            newfilepath = filePath + File.separatorChar + fileName;


            System.out.println("newfilepath=" + newfilepath);
            File dest = new File(filePath);
            if (!dest.exists()) {
                dest.mkdirs();
            }
            File uploadFile = new File(newfilepath);
            if (uploadFile.exists()) {
                uploadFile.delete();
            }
            try {

                log.info("start upload file: " + fileName);
                FileCopyUtils.copy(mf.getBytes(), uploadFile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                log.info("upload failed. filename: " + fileName + e.getMessage());
                return null;
            }
            filePathList.add(filePathUrl+"/"+fileName);
        }

        return filePathList;
    }
}
