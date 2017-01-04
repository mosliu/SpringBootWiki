package net.liuxuan.SprKi.controller.utils;

import com.baidu.ueditor.ActionEnter;
import net.liuxuan.spring.Helper.SystemHelper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.utils.UEditorController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/9 11:02
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/9  |    Moses       |     Created
 */
@Controller
public class UEditorController {
    private static Logger log = LoggerFactory.getLogger(UEditorController.class);

    /**
     * The Context.
     */
    @Autowired
    ServletContext context;

    /**
     * Do action.
     *
     * @param action   the action
     * @param request  the request
     * @param response the response
     */
    @ResponseBody
    @RequestMapping(value = "/utils/ueditor", produces = "application/json")
    public void doAction(
            @RequestParam(defaultValue = "") String action,
            HttpServletRequest request, HttpServletResponse response) {

//        String rootPath = context.getRealPath("/");
        String rootPath = SystemHelper.getRootPath()+"/static/";
//        log.info("===rootPath:{} , action:{}", rootPath, action);
        //修改了ActionEnter
        ActionEnter actionEnter = new ActionEnter(request, rootPath);
        String back = actionEnter.exec();
        JSONObject jsonConfig = new JSONObject(back);
//        Gson g = new Gson();
//        back = StringEscapeUtils.unescapeJava(back);
//        log.trace("===Action is {} , Return message is:{}", action, StringEscapeUtils.unescapeJava(back));
//        log.info("===back message is:{}", back);
//        return jsonConfig.toString();
        try {
            response.getWriter().write(back);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        return back;
    }

}
