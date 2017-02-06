package net.liuxuan.SprKi.controller.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.utils.UploadFileController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/23 15:45
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/23 |    Moses       |     Created
 */
@RestController
@RequestMapping("/upload")
public class UploadFileController {

    @Value("${SprKi.upload.savepathroot}")
    private String picSavePathRoot;
    @Value("${SprKi.upload.savepathchild}")
    private String picSavePathChild;

//    @Autowired
//    CommonsMultipartResolver multipartResolver;

    private static Logger log = LoggerFactory.getLogger(UploadFileController.class);

    @Secured("ROLE_USER")
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public ModelAndView getImageUploadURL(){
        return new ModelAndView("common/uploadimage");
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
//    @Secured("${SprKi.editor.acl}")
    @Secured("ROLE_USER")
    public String handleUploadProcess(
            @RequestParam("image") MultipartFile file, Model model)
            throws Exception {
        log.debug("Entered /upload/image UploadFileController.handleUploadProcess() ");
        file.transferTo(new File("d:/img.jpg"));
//        FileOutputStream out = new FileOutputStream(new File("d:/img.jpg"));
//        FileCopyUtils.copy(file,out);
//        BufferedOutputStream bos = new BufferedOutputStream(out);
//
//        out.write(file.getInputStream().read());

        model.addAttribute("success", "true");
        StringBuilder sb = new StringBuilder();
        sb.append("<script>top.$('.mce-btn.mce-open').parent().find('.mce-textbox').val('")
                .append("/images/test1.jpg")
                .append("').closest('.mce-window').find('.mce-primary').click();</script> ");

        return sb.toString();
    }

    /**
     *接受一个文件，返回一个文件地址
     *
     */
    @RequestMapping(value = "/image2", method = RequestMethod.POST)
//    @Secured("${SprKi.editor.acl}")
    @Secured("ROLE_USER")
    public String handleUploadProcess2(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
        MultipartFile imgFile1  =  multipartRequest.getFile("image");
        log.debug("-Entered /upload/image2 UploadFileController.handleUploadProcess2() ");
        File path = new File(picSavePathRoot + picSavePathChild +"/");
        path.mkdirs();
//        System.out.println(path.getAbsolutePath());
//        if(path.exists()){
//        }else{
//            path.createNewFile();
//            System.out.println("create new path:"+path.getAbsolutePath());
//        }

        String storename = System.currentTimeMillis()+imgFile1.getOriginalFilename();
        File saveimg = new File(path.getAbsolutePath()+"/"+storename);
//        saveimg.createNewFile();
//        System.out.println(path.getAbsolutePath());
//        imgFile1.transferTo(new File("d:/img2.jpg"));
        imgFile1.transferTo(saveimg);
        System.out.println(saveimg.getAbsolutePath());
        System.out.println(saveimg.exists());
//        FileOutputStream out = new FileOutputStream(new File("d:/img.jpg"));
//        FileCopyUtils.copy(file,out);
//        BufferedOutputStream bos = new BufferedOutputStream(out);
//
//        out.write(file.getInputStream().read());

        model.addAttribute("success", "true");
        StringBuilder sb = new StringBuilder();
        sb.append(picSavePathChild +storename);
        log.debug("uploaded file {},stored at ",storename,saveimg.getAbsolutePath());

        return sb.toString();
    }


    @RequestMapping("/fileUpload2.do")
    public String fileUpload2(HttpServletRequest request,HttpServletResponse response){
        long startTime=System.currentTimeMillis();   //获取开始时间

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        if(multipartResolver.isMultipart(request)){ //判断request是否有文件上传
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            Iterator<String> ite = multiRequest.getFileNames();
            while(ite.hasNext()){
                MultipartFile file = multiRequest.getFile(ite.next());
                if(file!=null){
                    File localFile = new File("D:/"+file.getOriginalFilename());
                    try {
                        file.transferTo(localFile); //将上传文件写到服务器上指定的文件
                    } catch (IllegalStateException e) {
                        log.error("IllegalStateException!",e);
//                        e.printStackTrace();
                    } catch (IOException e) {
                        log.error("IOException!",e);
//                        e.printStackTrace();
                    }
                }
            }
        }
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("上传文件共使用时间："+(endTime-startTime));

        return "success";
    }

}
