package net.liuxuan.supportsystem.controller;

import net.liuxuan.spring.helper.SpringContextHelper;
import net.liuxuan.supportsystem.service.labthink.DepartmentService;
import net.liuxuan.supportsystem.service.user.UserDetailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.CommonController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/5/12 8:26
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/5/12  |    Moses       |     Created
 */
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CommonController {

    @Value("${SprKi.upload.restrictedpath}")
    private String restrictedFilePath;
    /**
     * The Request mapping handler mapping.
     */
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;


    private Set<String> UrlMappingList;

    /**
     * Init.
     */
    @PostConstruct
    public void init(){
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> requestMappingInfos = handlerMethods.keySet();
        UrlMappingList = requestMappingInfos
                .stream()
                .map(e -> e.getPatternsCondition().getPatterns())
                .collect(Collectors.toList()).stream()
                .flatMap(e -> e.stream())
                .collect(Collectors.toSet());
    }

    /**
     * Gets msg.
     *
     * @param path     the path
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @return the msg
     */
    @RequestMapping(value = "/goget/{path}", method = RequestMethod.GET)
    public String getMsg(
            @PathVariable String path,
            HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        System.out.println("PATH IS:"+path);
        path = path.replaceAll("-","/");
        System.out.println(UrlMappingList.contains("/"+path));
        if(UrlMappingList.contains("/"+path)){
            return "redirect:/"+path;
        }

        System.out.println("PATH TRANSLATE TO:"+path);

        return path;
    }

    @RequestMapping(value = "/moses", method = RequestMethod.GET)
    public String mosesParse(){
        return "tools/parse";
    }


    @RequestMapping(value = "/func/get", method = RequestMethod.GET)
    @ResponseBody
    public String getFile(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model){
        String path = request.getParameter("file");
        path = restrictedFilePath +'/'+path;
        File f = new File(path);
        String rtn;
        if(f.exists()&&(!f.isDirectory())){
            rtn = path;
        }else{
            rtn = path+ "  :   Not Existed!!!";
        }

        return rtn;
    }
    @RequestMapping(value = "/func/get2", method = RequestMethod.GET)
//    @ResponseBody
    public void getFile2(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws IOException {
        String path = request.getParameter("file");
        path = restrictedFilePath +'/'+path;
        File f = new File(path);
//        String rtn =null;
        if(f.exists()&&(!f.isDirectory())){
//            rtn = path;
        }else{
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }

        String mimeType= URLConnection.guessContentTypeFromName(f.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }

        System.out.println("mimetype : "+mimeType);
//        mimeType = "application/force-download";
        response.setContentType(mimeType);
        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
//        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + f.getName() +"\""));
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + f.getName() +"\""));


        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

        response.setContentLength((int)f.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(f));

        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());

    }



    /**
     * 为所有的用户加上头像
     *
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @return string
     */
    @RequestMapping(value = "/func/add_avatar", method = RequestMethod.GET)
    @ResponseBody
    public String addAvatar(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model){
        UserDetailInfoService userDetailInfoService = (UserDetailInfoService) SpringContextHelper.getBean("userDetailInfoServiceImpl");
        userDetailInfoService.checkAllUserDetailInfoAvatar();
        return "OK";
    }

    /**
     * Add department role string.
     *
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @return the string
     */
    @RequestMapping(value = "/func/addDepartmentRole", method = RequestMethod.GET)
    @ResponseBody
    public String addDepartmentRole(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model){
        DepartmentService departmentServiceImpl = (DepartmentService) SpringContextHelper.getBean("departmentServiceImpl");
        departmentServiceImpl.checkDeparmentRole();
        return "OK";
    }

}
