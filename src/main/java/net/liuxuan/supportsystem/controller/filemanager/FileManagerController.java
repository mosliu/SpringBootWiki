package net.liuxuan.supportsystem.controller.filemanager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipOutputStream;

/**
 * Copyright (c) 2010-2018.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.filemanager.FileManagerController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2018/1/3 13:11
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2018/1/3  |    Moses       |     Created
 */

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping(value = "/fm")
public class FileManagerController {
    /*
     *  文件管理根目录,此处为了方便采用Hard Code
     */
//    public static String ROOT = "F:\\SSS\\";
    public static String ROOT = "F:/SSS";

    @Value("${SprKi.filemanage.basepath}")
    private String basePath;

    //获取发送短消息界面
    private static Logger log = LoggerFactory.getLogger(FileManagerController.class);

    @RequestMapping("/")
    public String news(HttpServletRequest request,
                       HttpServletResponse response, Map<String, Object> model) {
        //分页 必须用datas 或者改页面标签
        model.put("datas", null);
        return "fm/fm_index";
    }

    /**
     * 展示文件列表
     */
    @RequestMapping("list")
    public ResponseEntity<JsonObject> list(@RequestBody JsonObject json, HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            // 需要显示的目录路径
//            String path = "/";
            String path = json.get("path") == null ? "" : json.get("path").getAsString();
            if (StringUtils.isBlank(path)) {
                path = "/";
            }

            // 返回的结果集
            JsonArray fileItems = new JsonArray();
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(basePath, path));
            String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat dt = new SimpleDateFormat(DATE_FORMAT);
            for (Path pathObj : directoryStream) {
                // 获取文件基本属性
                BasicFileAttributes attrs = Files.readAttributes(pathObj, BasicFileAttributes.class);

                // 封装返回JSON数据
                JsonObject fileItem = new JsonObject();
                fileItem.addProperty("name", pathObj.getFileName().toString());
                fileItem.addProperty("rights", FMFileUtils.getPermissions(pathObj)); // 文件权限
                fileItem.addProperty("date", dt.format(new Date(attrs.lastModifiedTime().toMillis())));
                fileItem.addProperty("size", attrs.size());
                fileItem.addProperty("type", attrs.isDirectory() ? "dir" : "file");
                fileItems.add(fileItem);
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("result", fileItems);
//            ResponseHelper.writeObjectToResponseAsJson(response, jsonObject);
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
//            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(error(e.getMessage()), HttpStatus.OK);
//            return error(e.getMessage());
        }


    }



    /**
     * 文件上传
     */
    @RequestMapping("upload")
    @ResponseBody
    public JsonObject upload(@RequestParam("destination") String destination, HttpServletRequest request) {

        try {
            // Servlet3.0方式上传文件
            Collection<Part> parts = request.getParts();

            for (Part part : parts) {
                if (part.getContentType() != null) {  // 忽略路径字段,只处理文件类型
                    String path = basePath + destination;

                    File f = new File(path, FMFileUtils.getFileName(part.getHeader("content-disposition")));
                    if (!FMFileUtils.write(part.getInputStream(), f)) {
                        throw new Exception("文件上传失败");
                    }
                }
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 文件下载/预览
     */
    @RequestMapping("preview")
    public void preview(HttpServletResponse response, String path) throws IOException {

        File file = new File(basePath, path);
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource Not Found");
            return;
        }

        /*
         * 获取mimeType
         */
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", URLEncoder.encode(file.getName(), "UTF-8")));
        response.setContentLength((int) file.length());

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }

    /**
     * 创建目录
     */
    @RequestMapping("createFolder")
    @ResponseBody
    public JsonObject createFolder(@RequestBody JsonObject json) {
        try {
            String newPath = json.get("newPath") == null ? "" : json.get("newPath").getAsString();
            File newDir = new File(basePath + newPath);
            if (!newDir.mkdir()) {
                throw new Exception("不能创建目录: " + newPath);
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改文件或目录权限
     */
    @RequestMapping("changePermissions")
    @ResponseBody
    public JsonObject changePermissions(@RequestBody JsonObject json) {
        try {

            String perms = json.get("perms") == null ? "" : json.get("perms").getAsString(); // 权限
            boolean recursive = json.get("recursive").getAsBoolean(); // 子目录是否生效

            JsonArray items = json.get("items").getAsJsonArray();
            //for (int i = 0; i < items.length(); i++) {
            for (int i = 0; i < items.size(); i++) {
                String path = items.get(i).getAsString();
                File f = new File(basePath, path);
                FMFileUtils.setPermissions(f, perms, recursive); // 设置权限
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 复制文件或目录
     */
    @RequestMapping("copy")
    @ResponseBody
    public JsonObject copy(@RequestBody JsonObject json, HttpServletRequest request) {
        try {
            String newpath = json.get("newPath") == null ? "" : json.get("newPath").getAsString();
            JsonArray items = json.get("items").getAsJsonArray();

            for (int i = 0; i < items.size(); i++) {
                String path = items.get(i).getAsString();

                File srcFile = new File(basePath, path);
                File destFile = new File(basePath + newpath, srcFile.getName());

                FileCopyUtils.copy(srcFile, destFile);
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 移动文件或目录
     */
    @RequestMapping("move")
    @ResponseBody
    public JsonObject move(@RequestBody JsonObject json) {
        try {
            String newpath = json.get("newPath") == null ? "" : json.get("newPath").getAsString();
            JsonArray items = json.getAsJsonArray("items");

            for (int i = 0; i < items.size(); i++) {
                String path = items.get(i).getAsString();

                File srcFile = new File(basePath, path);
                File destFile = new File(basePath + newpath, srcFile.getName());

                if (srcFile.isFile()) {
                    FileUtils.moveFile(srcFile, destFile);
                } else {
                    FileUtils.moveDirectory(srcFile, destFile);
                }
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 删除文件或目录
     */
    @RequestMapping("remove")
    @ResponseBody
    public JsonObject remove(@RequestBody JsonObject json) {
        try {
            JsonArray items = json.getAsJsonArray("items");
            for (int i = 0; i < items.size(); i++) {
                String path = items.get(i).getAsString();
                File srcFile = new File(basePath, path);
                if (!FileUtils.deleteQuietly(srcFile)) {
                    throw new Exception("删除失败: " + srcFile.getAbsolutePath());
                }
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 重命名文件或目录
     */
    @RequestMapping("rename")
    @ResponseBody
    public JsonObject rename(@RequestBody JsonObject json) {
        try {
            String path = json.get("item") == null ? "" : json.get("item").getAsString();
            String newPath = json.get("newItemPath") == null ? "" : json.get("newItemPath").getAsString();

            File srcFile = new File(basePath, path);
            File destFile = new File(basePath, newPath);
            if (srcFile.isFile()) {
                FileUtils.moveFile(srcFile, destFile);
            } else {
                FileUtils.moveDirectory(srcFile, destFile);
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 查看文件内容,针对html、txt等可编辑文件
     */
    @RequestMapping("getContent")
    @ResponseBody
    public JsonObject getContent(@RequestBody JsonObject json) {
        try {
            String path = json.get("item") == null ? "" : json.get("item").getAsString();
            File srcFile = new File(basePath, path);

            String content = FileUtils.readFileToString(srcFile);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("result", content);
            return jsonObject;
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改文件内容,针对html、txt等可编辑文件
     */
    @RequestMapping("edit")
    @ResponseBody
    public JsonObject edit(@RequestBody JsonObject json) {
        try {
            String path = json.get("item") == null ? "" : json.get("item").getAsString();
            String content = json.get("content") == null ? "" : json.get("content").getAsString();

            File srcFile = new File(basePath, path);
            FileUtils.writeStringToFile(srcFile, content);

            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 文件压缩
     */
    @RequestMapping("compress")
    @ResponseBody
    public JsonObject compress(@RequestBody JsonObject json) {
        try {
            String destination = json.get("destination") == null ? "" : json.get("destination").getAsString();
            String compressedFilename = json.get("compressedFilename") == null ? "" : json.get("compressedFilename").getAsString();
            JsonArray items = json.getAsJsonArray("items");
            List<File> files = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                File f = new File(basePath, items.get(i).getAsString());
                files.add(f);
            }

            File zip = new File(basePath + destination, compressedFilename);

            try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip))) {
                ZipUtils.zipFiles(out, "", files.toArray(new File[files.size()]));
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 文件解压
     */
    @RequestMapping("extract")
    @ResponseBody
    public JsonObject extract(@RequestBody JsonObject json) {
        try {
            String destination = json.get("destination") == null ? "" : json.get("destination").getAsString();
            String zipName = json.get("item") == null ? "" : json.get("item").getAsString();
            String folderName = json.get("folderName") == null ? "" : json.get("folderName").getAsString();
            File file = new File(basePath, zipName);

            String extension = FMFileUtils.getExtension(zipName);
            switch (extension) {
                case ".zip":
                    ZipUtils.unZipFiles(file, basePath + destination);
                    break;
                case ".gz":
                    TargzUtils.unTargzFile(file, basePath + destination);
                    break;
                case ".rar":
                    RarUtils.unRarFile(file, basePath + destination);
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }


    private JsonObject error(String msg) {
        // { "result": { "success": false, "error": "msg" } }
        JsonObject result = new JsonObject();
        JsonObject jsonObject = new JsonObject();
        result.addProperty("success", false);
        result.addProperty("error", msg);

        jsonObject.add("result", result);
        return jsonObject;

    }


    private JsonObject success() {
        // { "result": { "success": true, "error": null } }
        JsonObject result = new JsonObject();
        JsonObject jsonObject = new JsonObject();
        result.addProperty("success", true);
        result.addProperty("error", "");

        jsonObject.add("result", result);

        return jsonObject;
    }
}
