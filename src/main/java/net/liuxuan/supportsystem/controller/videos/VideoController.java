package net.liuxuan.supportsystem.controller.videos;

import net.liuxuan.spring.helper.SystemHelper;
import net.liuxuan.supportsystem.entity.CMSVideo;
import net.liuxuan.supportsystem.service.CMSVideoService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.ticket.TicketController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/9/30 11:27
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/9/30  |    Moses       |     Created
 */
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class VideoController {
    private static Logger log = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private CMSVideoService cmsVideoService;


    @RequestMapping(value = "/video/list", method = RequestMethod.GET)
    public String getVideoList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        List<CMSVideo> allContents = cmsVideoService.getAllCMSVideo();
        log.info("video list size is {}", allContents.size());
        model.put("alllist", allContents);
        return "video/video_list";
    }

    @RequestMapping(value = "/video/{id}", method = RequestMethod.GET)
    public String getVideoById(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        CMSVideo content = cmsVideoService.findCMSVideoById(id);
        model.put("content", content);
        return "video/video_show";

    }

    @RequestMapping(value = "/video/videoxml/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String getVideoXml(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {

        String originxml =
                "<vcastr>" +
                        //channel
                        "<channel> <item>" +
                        "<source>http://vcastr.ruochi.com/video/happy_feet.flv</source>" +
                        "<duration></duration>" + //对应影片的总时间, 单位是秒，由于有些影片在制作过程中时间信息丢失，可以通过这里来设置
                        "<title></title>" + //对应影片的标题
                        "</item> </channel>" +
                        //config
                        "<config>" +
                        "<bufferTime>4</bufferTime>" + //在缓存区影片的时间,单位是秒
                        "<contralPanelAlpha>0.75</contralPanelAlpha>" + //控制栏的透明度，在 0 — 1 之间
                        "<controlPanelBgColor>0xff6600</controlPanelBgColor>" + //控制栏背景的颜色
                        "<controlPanelBtnColor>0xffffff</controlPanelBtnColor>" + //控制按钮的颜色
                        "<contralPanelBtnGlowColro>0xffff00</contralPanelBtnGlowColro>" + //控制按钮光晕的颜色
                        //控制栏的显示模式，
                        // "float": 默认的浮动模式,鼠标移除播放器就会隐藏掉
                        // "normal": 正常模式，任何时候都在影片的下部浮动显示
                        // "bottom": 下部模式，不浮动在影片之上，而是显示在影片下方
                        // "none": 不显示播放器控制栏
                        "<controlPanelMode>float</controlPanelMode>" +
                        "<defautVolume>0.8</defautVolume>" +//默认的声音大小,最大为1,最小为0
                        "<isAutoPlay>true</isAutoPlay>" +//是否影片自动开始播放,默认是true
                        "<isLoadBegin>true</isLoadBegin>" + //是否一开始就读取影片,默认是true
                        "<isRepeat>false</isRepeat>" +//是否循环播放影片,默然是false
                        "<isShowAbout>true</isShowAbout>" + // 是否显示关于信息
                        // 影片放缩模式:
                        //"showAll": 可以看到全部影片,保持比例,可能上下或者左右
                        //"exactFil": 放缩影片到播放器的尺寸,可能比例失调
                        //"noScale": 影片的原始尺寸,无放缩
                        //"noBorder": 影片充满播放器,保持比例,可能会被裁剪
                        "<scaleMode>showAll</scaleMode>" +
                        "</config>" +
                        //plugIns
                        "<plugIns>" +
                        "<logoPlugIn>" +
                        "<url>/ui/mosesadd/vcastr/logoPlugIn.swf</url>" +
                        "<logoText>www.ruochi.com</logoText>" +
                        "<logoTextAlpha>0.75</logoTextAlpha>" +
                        "<logoTextFontSize>30</logoTextFontSize>" +
                        "<logoTextLink>http://192.168.7.202</logoTextLink>" +
                        "<logoTextColor>0xffffff</logoTextColor>" +
                        "<textMargin>20 20 auto auto</textMargin>" +
                        "</logoPlugIn>" +
                        //end of plugIns 
                        "</plugIns>" +
                        "</vcastr>";


        try {
            Document document = DocumentHelper.parseText(originxml);
            document.getRootElement().element("channel").element("item").element("source").setText("/video/getVideo/" + id);
            //Not Support Chinese
            String username = SystemHelper.GetCurrentUserName();
            document.getRootElement().element("plugIns").element("logoPlugIn").element("logoText").setText(username);
            return document.asXML();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return "";

    }

    @RequestMapping(value = "/video/getVideo/{id}", method = RequestMethod.GET)
//    @ResponseBody
    public void getMediaFile(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws IOException {

        CMSVideo content = cmsVideoService.findCMSVideoById(id);
        if (content == null) {
            //todo do sth
        }
        String path = content.getCmsVideoFilepath();
//        Path p = Paths.get(path);

        File f = new File(path);
//        String rtn;
        if (!(f.exists() && (!f.isDirectory()))) {
//            rtn = path;
//        } else {
            String errorMessage = "Sorry. The file you are looking for does not exist";
//            System.out.println(errorMessage);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }

        String mimeType = URLConnection.guessContentTypeFromName(f.getName());
        if (mimeType == null) {
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }

        System.out.println("mimetype : " + mimeType);
//        mimeType = "application/force-download";
        response.setContentType(mimeType);
        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + f.getName() + "\""));
//        response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + f.getName() +"\""));


        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

        response.setContentLength((int) f.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(f));

        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());

    }

}
