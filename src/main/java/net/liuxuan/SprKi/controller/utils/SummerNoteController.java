package net.liuxuan.SprKi.controller.utils;

import com.baidu.ueditor.ActionEnter;
import net.liuxuan.utils.upload.UploadUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.logging.LogFactory.getLog;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.utils.SummerNoteController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/11/11 10:21
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/11/11  |    Moses       |     Created
 */
@Controller
public class SummerNoteController {
    private static Log logger = getLog(SummerNoteController.class);


    @Value("${SprKi.upload.savepathroot}")
    private String picSavePathRoot;
    @Value("${SprKi.upload.savepathchild}")
    private String picSavePathChild;
    @Value("${SprKi.upload.accesspath}")
    private String picAccessPath;
    /**
     * The Context.
     */
//    @Autowired
//    ServletContext context;


    /**
     *
     * @param files
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/utils/uploadMultipleFile", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public void uploadMultipleFileHandler(@RequestParam("file") MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        File path = new File(picSavePathRoot + picSavePathChild +"/");
//        String back = UploadUtil.uploadImage(request.getServletContext().getRealPath("/"), files);

        String back = UploadUtil.uploadImage(picSavePathRoot + picSavePathChild +"/",picAccessPath, files);
        try {
            response.getWriter().write(back);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
