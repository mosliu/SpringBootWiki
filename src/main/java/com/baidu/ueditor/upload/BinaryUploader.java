package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BinaryUploader {

    private static Logger log =  LoggerFactory.getLogger(BinaryUploader.class);
    
    public static final State save(HttpServletRequest request,
                                   Map<String, Object> conf)  {
//		FileItemStream fileStream = null;
        boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;


        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }

//		ServletFileUpload upload = new ServletFileUpload(
//				new DiskFileItemFactory());

//        if ( isAjaxUpload ) {
//            upload.setHeaderEncoding( "UTF-8" );
//        }



        //Moses rewrite
        //重写后台 处理上传
        try{
            if (isAjaxUpload) {
//            upload.setHeaderEncoding( "UTF-8" );
                multipartRequest.setCharacterEncoding("UTF-8");
            }
            //先取得文件
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

            //没有文件则返回失败
            if(fileMap.size()==0){
                return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
            }
            String savePath= (String) conf.get("savePath");
            //取一个文件 默认一个
            MultipartFile multipartFile = null;
            for (String s : fileMap.keySet()) {
                log.debug("Upload contains files:{}",s);
                multipartFile = fileMap.get(s);
            }
            String originFileName = multipartFile.getOriginalFilename();
            String suffix = FileType.getSuffixByFilename(originFileName);
            originFileName = originFileName.substring(0,
                    originFileName.length() - suffix.length());
            savePath = savePath + suffix;
            long maxSize = ((Long) conf.get("maxSize")).longValue();

            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
            }

            savePath = PathFormat.parse(savePath, originFileName);

            String physicalPath = (String) conf.get("rootPath") + savePath;


            InputStream is = multipartFile.getInputStream();
            State storageState = StorageManager.saveFileByInputStream(is,
                    physicalPath, maxSize);
            is.close();

            if (storageState.isSuccess()) {
                storageState.putInfo("url", PathFormat.format(savePath));
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", originFileName + suffix);
            }

            return storageState;
        } catch (IOException e) {
            e.printStackTrace();
        }


//        try {
//            FileItemIterator iterator = upload.getItemIterator(request);

//            while (iterator.hasNext()) {
//                fileStream = iterator.next();
//
//                if (!fileStream.isFormField())
//                    break;
//                fileStream = null;
//            }

//            if (fileStream == null) {
//                return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
//            }

//            String savePath = (String) conf.get("savePath");
//            String originFileName = fileStream.getName();
//            String suffix = FileType.getSuffixByFilename(originFileName);
//
//            originFileName = originFileName.substring(0,
//                    originFileName.length() - suffix.length());
//            savePath = savePath + suffix;

//            long maxSize = ((Long) conf.get("maxSize")).longValue();
//
//            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
//                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
//            }
//
//            savePath = PathFormat.parse(savePath, originFileName);
//
//            String physicalPath = (String) conf.get("rootPath") + savePath;
//
//            InputStream is = fileStream.openStream();
//            State storageState = StorageManager.saveFileByInputStream(is,
//                    physicalPath, maxSize);
//            is.close();
//
//            if (storageState.isSuccess()) {
//                storageState.putInfo("url", PathFormat.format(savePath));
//                storageState.putInfo("type", suffix);
//                storageState.putInfo("original", originFileName + suffix);
//            }
//
//            return storageState;
//        } catch (FileUploadException e) {
//            return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);

        return list.contains(type);
    }
}
