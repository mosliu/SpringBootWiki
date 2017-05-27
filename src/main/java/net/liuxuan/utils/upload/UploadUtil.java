package net.liuxuan.utils.upload;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.utils.upload.UploadUtil
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/11/11 10:24
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/11/11  |    Moses       |     Created
 */
public class UploadUtil {
    private static Logger log = LoggerFactory.getLogger(UploadUtil.class);


    /**
     * Summernote调用，上传一个文件，保存在disk上，返回一个文件地址。
     *
     * @param storeDir    存储路径
     * @param accessPath  web访问路径
     * @param uploadFiles 传入的文件
     * @return Json格式内容
     */
    public static String uploadImage(String storeDir, String accessPath, MultipartFile[] uploadFiles) {
        List<MultipartFile> multipartFiles = Arrays.asList(uploadFiles);
        List<String> accessPathList = uploadFiles(storeDir, accessPath, multipartFiles, new CurrentTimeNamer());

        //返回的Json载体
        String images = "{}";
        JsonArray jarry = new JsonArray();

        for (int i = 0; i < accessPathList.size(); i++) {
            //构造返回的Json数据。
            JsonObject obj = new JsonObject();
            obj.addProperty(String.valueOf(i), accessPathList.get(i));//保存的图片路径
            jarry.add(obj);
        }

        images = jarry.toString();
//        System.out.println(images);
        return images;
    }

    /**
     * 上传文件，保存在disk上，返回文件地址。
     *
     * @param storeDir      存储路径
     * @param accessPath    web访问路径
     * @param multipartFiles   传入的文件
     * @param nameGenerater 存盘文件名称生成逻辑
     * @return 访问地址的String 的List
     */
    public static List<String> uploadFiles(String storeDir, String accessPath, List<MultipartFile> multipartFiles, UploadFileNameGenerater nameGenerater) {
        //serverPath:F:\SyncDisk\Dropbox\Workspaces\Maven\SpringBootTest\target\classes\static\
        //String uploadPath = serverPath + getImageRelativePath();
        File storePath = checkStorePath(storeDir);
        //web访问路径，检查末尾。
        if (!accessPath.endsWith("/")) {
            accessPath = accessPath + "/";
        }
        //使用lambda 需要一个未修改（类似于final）的字符串。
        String finalAccessPath = accessPath;

        List<String> accessPathList = new ArrayList<String>();

        multipartFiles.forEach(multipartFile -> {
            if (!multipartFile.isEmpty()) {
                String toFilename = nameGenerater.generate(multipartFile.getOriginalFilename());
                String savePath = storePath.getAbsolutePath() + File.separatorChar + toFilename;//保存的图片路径


                try {
                    save(multipartFile, savePath);
                    accessPathList.add(finalAccessPath + toFilename);
                } catch (IOException e) {
                    log.info("upload failed. filename: " + toFilename + e.getMessage());
//                    e.printStackTrace();
                }
            }
        });
        return accessPathList;
    }

    /**
     * 上传文件，保存在disk上，返回文件地址。
     *
     * @param storeDir      存储路径
     * @param accessPath    web访问路径
     * @param request   传入的request请求
     * @return 访问地址的String 的List
     */
    public static List<String> uploadFile(String storeDir, String accessPath, HttpServletRequest request) {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        List<MultipartFile> multipartFileList = multipartRequest.getFileMap().values().stream().collect(Collectors.toList());
        return uploadFiles(storeDir, accessPath, multipartFileList, new CurrentTimeNamer());
    }

    /**
     * 检查存储位置，无目录则创建,
     * 是文件则在名字最后加上Dir后重新检查。
     *
     * @param storeDir
     * @return 目的路径
     */
    public static File checkStorePath(String storeDir) {
        //如果不存在目录,创建一个目录
        File storePath = new File(storeDir);
        if (!storePath.exists()) {
            storePath.mkdirs();
        }

        if (storePath.isFile()) {
            return checkStorePath(storeDir + "Dir");
        } else {
            return storePath;
        }
    }

    /**
     * 将文件存储。
     *
     * @param uploadFile
     * @param savePath
     * @throws IOException
     */
    public static String save(MultipartFile uploadFile, String savePath) throws IOException {
        File saveimg = new File(savePath);
//        FileCopyUtils.copy(uploadFile.getBytes(), saveimg);
        uploadFile.transferTo(saveimg);
        return saveimg.getAbsolutePath();
    }

}
