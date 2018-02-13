package net.liuxuan.SprKi.controller.admin.labthink;

import net.liuxuan.SprKi.entity.DTO.BaseDTO;
import net.liuxuan.SprKi.entity.labthink.Devices;
import net.liuxuan.SprKi.entity.security.LogActionType;
import net.liuxuan.SprKi.entity.labthink.DeviceSubInfo;
import net.liuxuan.SprKi.service.labthink.DeviceSubInfoService;
import net.liuxuan.SprKi.service.labthink.DevicesService;
import net.liuxuan.spring.Helper.ResponseHelper;
import net.liuxuan.spring.Helper.SecurityLogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sun.audio.AudioDevice.device;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.controller.admin.labthink.labthink.DeviceSubInfoRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2018/02/13 09:33
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2018-02-13  |    Moses        |     Created
*/

@Controller
@RequestMapping("/admin")
public class DeviceSubInfoManagementController {
    private static Logger log = LoggerFactory.getLogger(DeviceSubInfoManagementController.class);

    @Autowired
    DeviceSubInfoService deviceSubInfoService;
    @Autowired
    DevicesService devicesService;

    @RequestMapping("deviceSubInfoManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages(Map<String, Object> model) {
        DeviceSubInfo deviceSubInfo = new DeviceSubInfo();
        model.put("deviceSubInfo", deviceSubInfo);
        List<Devices> allDevices = devicesService.getAllDevices();
        model.put("allDevices",allDevices);

        return "admin/" + "deviceSubInfoManage" + " :: middle";

    }

    @RequestMapping("deviceSubInfo")
    public String deviceSubInfoManage(@ModelAttribute("dto") BaseDTO dto, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model) throws IOException {
        log.info("===deviceSubInfoManage logged ,the _dto value is : {}", dto.toString());

        switch (dto.action) {
            case "edit":
                DeviceSubInfo deviceSubInfo;
                Long id = dto.getStr2LongID();
                
                deviceSubInfo = deviceSubInfoService.findDeviceSubInfoById(id);
                if (deviceSubInfo != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                List<Devices> allDevices = devicesService.getAllDevices();
                model.put("allDevices",allDevices);
                model.put("deviceSubInfo", deviceSubInfo);
                return "admin/snipplets/div_deviceSubInfo :: deviceSubInfoedit";
            default:
                return "redirect:/admin/deviceSubInfo_ajax";
//                break;
        }
    }


    @RequestMapping("deviceSubInfo_ajax")
//    @ResponseBody
    public void deviceSubInfoManageAjax(@ModelAttribute("dto") BaseDTO _dto, DeviceSubInfo _deviceSubInfo, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===deviceSubInfoManageAjax logged ,the value is : {}", _dto.toString());
        Long id = _dto.getStr2LongID();

//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                String deviceSubInfoName = request.getParameter("deviceSubInfoName");
                String deviceSubInfoNameCN = request.getParameter("deviceSubInfoNameCN");
                String comment = request.getParameter("comment");
                boolean deviceSubInfoExists = deviceSubInfoService.checkDeviceSubInfoExists(deviceSubInfoName);
                if (deviceSubInfoExists) {
                    log.info("===deviceSubInfoManageAjax logged ,添加DeviceSubInfo已存在 : {}");
                    rtnData.put("error", "ERROR_DeviceSubInfoExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "添加DeviceSubInfo已存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加DeviceSubInfo");
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, _deviceSubInfo, "添加角色", "");
                    deviceSubInfoService.saveDeviceSubInfo(_deviceSubInfo);
                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除角色", "");
                boolean b = deviceSubInfoService.deleteDeviceSubInfoById(id);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除DeviceSubInfo");
                } else {
                    rtnData.put("error", "ERROR_DeviceSubInfoNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "DeviceSubInfo不存在，删除失败");
                }
                break;
            case "update":
                deviceSubInfoService.saveDeviceSubInfo(_deviceSubInfo);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _deviceSubInfo, "更新DeviceSubInfo", "");
                rtnData.put("success1", "success!");
                break;
            default:

                break;
        }
//        return "";
//        mapper.writeValue(response.getWriter(), rtnData);
        ResponseHelper.writeMapToResponseAsJson(response, rtnData);
    }


    @ModelAttribute("DeviceSubInfo_list")
    public List<DeviceSubInfo> DeviceSubInfolist() {
        return deviceSubInfoService.getAllDeviceSubInfo();
    }
}