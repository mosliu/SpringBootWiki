package net.liuxuan.SprKi.controller.supporttools;

import net.liuxuan.utils.OCRUtils;
import net.liuxuan.utils.OCRUtils2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.supporttools.OCRController
 * 功能: OCR功能提供
 * 版本:	@version 1.0
 * 编制日期: 2016/11/29 9:30
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/11/29  |    Moses       |     Created
 */
@Controller
@RequestMapping("/tools")
public class OCRController {
//    private static Log logger = LogFactory.getLog(OCRController.class);
    private static Logger log =  LoggerFactory.getLogger(OCRController.class);
    @RequestMapping("/OCR")
    public String getOCR(HttpServletRequest request,
                               HttpServletResponse response, Map<String, Object> model){

        return "tools/ocr";
    }
    //ocrImgUpload
    /**
     *
     * @param file
     * @param type
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/ocrImgUpload", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public void uploadFileHandler(@RequestParam("file") String file,@RequestParam("type") String type, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        File path = new File(picSavePathRoot + picSavePathChild +"/");
//        String back = UploadUtil.uploadImage(request.getServletContext().getRealPath("/"), files);
        int index =file.indexOf("base64,");
        if(index>0){
            file =file.substring(index+7);
        }
        String res = OCRUtils2.getOcrByBase64(file);
        try {
            response.getWriter().write(res);
        } catch (IOException e) {
            log.error("Io Exception!",e);
//            e.printStackTrace();
        }

    }
}
