package net.liuxuan.utils.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.liuxuan.spring.constants.SystemConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.utils.FreeMarkerUtil
 * 功能: 自动生成各个实体类
 * 版本:	@version 1.0
 * 编制日期: 2017/2/21 15:23
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/2/21  |    Moses       |     Created
 */
public class FreeMarkerUtil {
    private Configuration cfg;

    String model_name = "Message";
//    String subpackage = ".labthink";//如果需要放到labthink下，则为".labthink"
    String subpackage = "";//如果需要放到labthink下，则为".labthink"
    boolean createEntity,createRepository,createService,createAdminManagement;

    public static void main(String[] args) throws Exception {
        FreeMarkerUtil hf = new FreeMarkerUtil();
        hf.createEntity = true;
        hf.createRepository = true;
        hf.createService = true;
        hf.createAdminManagement = true;
        hf.init();
        hf.process(hf);
    }

    public void init() throws Exception {
        // 初始化FreeMarker配置
        // 创建一个Configuration实例
        cfg = new Configuration(Configuration.VERSION_2_3_0);
        // 设置FreeMarker的模版文件位置
        // 步骤一：指定 模板文件从何处加载的数据源，这里设置一个文件目录
        cfg.setDirectoryForTemplateLoading(new File(
                "F:/SyncDisk/Dropbox/Workspaces/Maven/SpringBootTest/src/main/java/net/liuxuan/utils/freemarker/templates/"));

//        cfg.setObjectWrapper(new DefaultObjectWrapper());
    }

    public void process(FreeMarkerUtil util) throws Exception {

        Map root = new HashMap();



        root.put("author", "Moses");
        root.put("subpackage", subpackage);
        root.put("date", new Date());

        root.put("model_name", model_name);//模块名称
        char a = model_name.charAt(0);
        root.put("model_name_firstSmall", model_name.replaceFirst(a + "", Character.toLowerCase(a) + ""));//模块名称
        root.put("table_name", "Sprki_"+model_name);//表名称


        String projectPath = "F:/SyncDisk/Dropbox/Workspaces/Maven/SpringBootTest/src/main/java/net/liuxuan/Sprki/";
        String templatePath = "F:/SyncDisk/Dropbox/Workspaces/Maven/SpringBootTest/src/main/resources/templates/admin/";

        String savePath;
        String fileName;
        Template template;
        if(createEntity) {
            savePath = getPathString("/entity/");
            fileName = model_name + ".java";
            template = cfg.getTemplate("entity.ftl");
            util.buildTemplate(root, projectPath, savePath, fileName, template);
        }

        if(createRepository) {
            fileName = model_name + "Repository.java";
            savePath = getPathString("/repository/");
            template = cfg.getTemplate("repository.ftl");
            util.buildTemplate(root, projectPath, savePath, fileName, template);
        }

        if(createService) {
            fileName = model_name + "Service.java";
            savePath = getPathString("/service/");
            template = cfg.getTemplate("service.ftl");
            util.buildTemplate(root, projectPath, savePath, fileName, template);
//
            fileName = model_name + "ServiceImpl.java";
            savePath = getPathString("/service/");
            template = cfg.getTemplate("serviceimpl.ftl");
            util.buildTemplate(root, projectPath, savePath, fileName, template);
        }

        if(createAdminManagement) {
            fileName = model_name + "ManagementController.java";
            savePath = ("/controller/admin/labthink/");
            template = cfg.getTemplate("managementcontroller.ftl");
            util.buildTemplate(root, projectPath, savePath, fileName, template);

            fileName = model_name + "Manage.html";
            savePath = ("/");
            template = cfg.getTemplate("adminManagePage.ftl");
            util.buildTemplate(root, templatePath, savePath, fileName, template);

            fileName = "div_"+model_name + ".html";
            savePath = ("/snipplets/");
            template = cfg.getTemplate("adminManageDivPage.ftl");
            util.buildTemplate(root, templatePath, savePath, fileName, template);
        }


    }

    public String getPathString(String savePath) {
        if(StringUtils.isNotBlank(subpackage)){
            savePath = savePath+subpackage.substring(1);

            if(savePath.endsWith("/")){

            }else{
                savePath = savePath+"/";
            }
        }

        return savePath;
    }

    public void buildTemplate(Map root, String projectPath, String savePath,
                              String fileName, Template template) throws IOException, TemplateException {
        String realFileName = projectPath + savePath + fileName;

        System.out.println("文件生成：" + realFileName);

        String realSavePath = projectPath + "/" + savePath;

        File newsDir = new File(realSavePath);
        if (!newsDir.exists()) {
            newsDir.mkdirs();
        }
        Writer out = null;
        // SYSTEM_ENCODING = "UTF-8";
        out = new OutputStreamWriter(new FileOutputStream(
                realFileName), SystemConstants.SYSTEM_ENCODING);

        template.process(root, out);
        if (out != null) {
            out.close();
        }

    }


}
