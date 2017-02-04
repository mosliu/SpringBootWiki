package net.liuxuan.utils.upload;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
    private static Log logger = LogFactory.getLog(UploadUtil.class);


    /**
     * 上传一个文件，保存在disk上，返回一个文件地址。
     *
     * @param storeDir    存储路径
     * @param accessPath  web访问路径
     * @param uploadFiles 传入的文件
     * @return Json格式内容
     */
    public static String uploadImage(String storeDir, String accessPath, MultipartFile[] uploadFiles) {
        //serverPath:F:\SyncDisk\Dropbox\Workspaces\Maven\SpringBootTest\target\classes\static\
//            String uploadPath = serverPath + getImageRelativePath();

        //如果不存在目录,创建一个目录
        File storePath = new File(storeDir);
        if (!storePath.exists()) {
            storePath.mkdirs();
        }

        //返回的Json载体
        String images = "{}";

        JsonArray jarry = new JsonArray();
        if (uploadFiles != null && uploadFiles.length > 0) {
            for (int i = 0; i < uploadFiles.length; i++) {
                MultipartFile uploadFile = uploadFiles[i];
                //save file
                if (!uploadFile.isEmpty()) {
//                        String savePath = getImageRelativePath() + file.getOriginalFilename();//数据库保存的图片路径
                    String toFilename = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
                    String savePath = storePath.getAbsolutePath() + "/" + toFilename;//保存的图片路径
                    if(!accessPath.endsWith("/")){
                        accessPath = accessPath + "/";
                    }
                    //构造返回的Json数据。
                    JsonObject obj = new JsonObject();
                    obj.addProperty(String.valueOf(i), accessPath+toFilename);//保存的图片路径
                    jarry.add(obj);
//                        images = JSONUtil.addProperty(images, String.valueOf(i), savePath);
                    save(uploadFile, savePath);
                }
            }
        }
        images = jarry.toString();
        System.out.println(images);
        return images;
    }

    private static void save(MultipartFile uploadFile, String savePath) {
        File saveimg = new File(savePath);
        try {
            uploadFile.transferTo(saveimg);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }


}
