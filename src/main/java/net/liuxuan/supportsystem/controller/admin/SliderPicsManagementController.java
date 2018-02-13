package net.liuxuan.supportsystem.controller.admin;

import net.liuxuan.spring.helper.ResponseHelper;
import net.liuxuan.spring.helper.SecurityLogHelper;
import net.liuxuan.supportsystem.entity.SliderPics;
import net.liuxuan.supportsystem.entity.dto.BaseDTO;
import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.supportsystem.service.SliderPicsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.controller.admin.labthink.SliderPicsRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/27 13:42
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-27  |    Moses        |     Created
*/

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class SliderPicsManagementController {
    private static Logger log = LoggerFactory.getLogger(SliderPicsManagementController.class);

    @Autowired
    private SliderPicsService sliderPicsService;

    @RequestMapping("sliderPicsManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages(Map<String, Object> model) {

        return "admin/" + "sliderPicsManage" + " :: middle";

    }

    @RequestMapping("sliderPics")
    public String sliderPicsManage(@ModelAttribute("dto") BaseDTO dto, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model) throws IOException {
        log.info("===sliderPicsManage logged ,the _dto value is : {}", dto.toString());

        switch (dto.action) {
            case "edit":
                SliderPics sliderPics;
                Long id = dto.getStr2LongID();
                
                sliderPics = sliderPicsService.findSliderPicsById(id);
                if (sliderPics != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("sliderPics", sliderPics);
                return "admin/snipplets/div_sliderPics :: sliderPicsedit";
            default:
                return "redirect:/admin/sliderPics_ajax";
//                break;
        }
    }

    @RequestMapping(value = "/sliderPicsList")
    @ResponseBody
    public List getLastestTenActivities() {

        List<SliderPics> sliderPicsList = sliderPicsService.getAllSliderPics();
        return sliderPicsList;
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//        List<SecurityLog> activities = securityLogService.getLastestTenActivities(0);
//        List<HashMap<String, String>> rtn = new ArrayList();
//        activities.forEach(node -> {
//            if (node.getLogInfoType().equals(FAQContent.class.getName())
//                    || node.getLogInfoType().equals(TicketContent.class.getName())) {
//
//                HashMap<String, String> map = new HashMap();
//                map.put("action", node.getAction());
//                map.put("username", node.getUsername());
//                map.put("url", node.getUrl());
//                map.put("operation", node.getOperation());
//                String jsonstr = node.getLogInfo();
//                ObjectMapper jsonMapper = new ObjectMapper();
//                try {
//                    JsonNode jsonNode = null;
//                    jsonNode = jsonMapper.readTree(jsonstr);
//                    map.put("title", jsonNode.get("title").textValue());
//                } catch (IOException e) {
//                    log.error("Json 处理出错", e);
////                    e.printStackTrace();
//                }
//                rtn.add(map);
//            }
//        });
//
//        return rtn;
    }



    @RequestMapping("sliderPics_ajax")
//    @ResponseBody
    public void sliderPicsManageAjax(@ModelAttribute("dto") BaseDTO _dto, SliderPics _sliderPics, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===sliderPicsManageAjax logged ,the value is : {}", _dto.toString());
        Long id = _dto.getStr2LongID();

//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                String sliderPicsName = request.getParameter("sliderPicsName");
                String sliderPicsNameCN = request.getParameter("sliderPicsNameCN");
                String comment = request.getParameter("comment");
                boolean sliderPicsExists = sliderPicsService.checkSliderPicsExists(sliderPicsName);
                if (sliderPicsExists) {
                    log.info("===sliderPicsManageAjax logged ,添加SliderPics已存在 : {}");
                    rtnData.put("error", "ERROR_SliderPicsExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "添加SliderPics已存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加SliderPics");
//
//                    SliderPics sliderPics = new SliderPics();
//                    sliderPics.setSliderPicsName(sliderPicsName);
//                    sliderPics.setComment(comment);
//                    sliderPics.setSliderPicsNameCN(sliderPicsNameCN);
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, _sliderPics, "添加轮展图片", "");
                    sliderPicsService.saveSliderPics(_sliderPics);
                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除轮展图片", "");
                boolean b = sliderPicsService.deleteSliderPicsById(id);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除SliderPics");
                } else {
                    rtnData.put("error", "ERROR_SliderPicsNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "SliderPics不存在，删除失败");
                }
                break;
            case "update":
                sliderPicsService.saveSliderPics(_sliderPics);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _sliderPics, "更新SliderPics", "");
                rtnData.put("success1", "success!");
                break;
            default:

                break;
        }
//        return "";
//        mapper.writeValue(response.getWriter(), rtnData);
        ResponseHelper.writeMapToResponseAsJson(response, rtnData);
    }


    @ModelAttribute("SliderPics_list")
    public List<SliderPics> SliderPicslist() {
        return sliderPicsService.getAllSliderPics();
    }
}