package net.liuxuan.SprKi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.UploadFileController
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
    private static Logger log = LoggerFactory.getLogger(UploadFileController.class);

    @RequestMapping(value = "/image", method = RequestMethod.POST)
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
                .append("').closest('.mce-window').find('.mce-primary').click();</script>");

        return sb.toString();
    }
}
