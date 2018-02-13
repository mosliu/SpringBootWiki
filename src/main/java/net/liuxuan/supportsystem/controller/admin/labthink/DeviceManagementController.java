package net.liuxuan.supportsystem.controller.admin.labthink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.liuxuan.supportsystem.entity.DTO.BaseDTO;
import net.liuxuan.supportsystem.entity.labthink.DeviceKind;
import net.liuxuan.supportsystem.entity.labthink.DeviceType;
import net.liuxuan.supportsystem.entity.labthink.Devices;
import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.supportsystem.service.labthink.DeviceKindService;
import net.liuxuan.supportsystem.service.labthink.DeviceTypeService;
import net.liuxuan.supportsystem.service.labthink.DevicesService;
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

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.admin.DeviceManagementController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/4/14 10:16
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/4/14  |    Moses       |     Created
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DeviceManagementController {
    private static Logger log = LoggerFactory.getLogger(DeviceManagementController.class);

    @Autowired
    DevicesService devicesService;

    @Autowired
    DeviceTypeService deviceTypeService;

    @Autowired
    DeviceKindService deviceKindService;


    @RequestMapping("deviceManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getDevicePages(Map<String, Object> model) {
        Devices device = new Devices();
        model.put("device", device);
        List<DeviceType> allDeviceType = deviceTypeService.getAllDeviceType();
        model.put("allDeviceType",allDeviceType);
        List<DeviceKind> allDeviceKind = deviceKindService.getAllDeviceKind();
        model.put("allDeviceKind",allDeviceKind);
        List<Devices> allDevices  = devicesService.getAllDevices();
        model.put("alllist",allDevices);
        return "admin/" + "deviceManage" + " :: middle";
    }

    @RequestMapping("deviceTypeManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getDeviceTypeManagePages(Map<String, Object> model) {
        List<DeviceType> alllist = deviceTypeService.getAllDeviceType();
        model.put("alllist",alllist);
        return "admin/" + "deviceTypeManage" + " :: middle";
    }

    @RequestMapping("deviceKindManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getDevicekindManagePages(Map<String, Object> model) {
        List<DeviceKind> alllist = deviceKindService.getAllDeviceKind();
        model.put("alllist",alllist);
        return "admin/" + "deviceKindManage" + " :: middle";
    }


    @RequestMapping("devices")
    public String deviceManage(@ModelAttribute("dto") BaseDTO dto, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model) throws IOException {
        log.info("===deviceManage logged ,the _dto value is : {}", dto.toString());

        switch (dto.action) {
            case "edit":
                Devices obj;
                Long id = dto.getStr2LongID();
                obj = devicesService.getDevicesById(id);
                if (obj != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("Devices", obj);
                List<DeviceType> allDeviceType = deviceTypeService.getAllDeviceType();
                model.put("allDeviceType",allDeviceType);
                List<DeviceKind> allDeviceKind = deviceKindService.getAllDeviceKind();
                model.put("allDeviceKind",allDeviceKind);
                return "admin/snipplets/div_device :: deviceedit";
            case "edit_DeviceType":
                DeviceType dt;
                dt = deviceTypeService.getDeviceTypeById(dto.getStr2LongID());
                if (dt != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("Devices", dt);
                return "admin/snipplets/div_devicetype :: devicetypeedit";
            case "edit_DeviceKind":
                DeviceKind dk;
                dk = deviceKindService.getDeviceKindById(dto.getStr2LongID());
                if (dk != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("Devices", dk);
                return "admin/snipplets/div_devicekind :: devicekindedit";
            default:
                return "redirect:/admin/Devices_ajax";
//                break;
        }
    }


    @RequestMapping("devices_ajax")
//    @ResponseBody
    public void deviceManageAjax(@ModelAttribute("dto") BaseDTO _dto, Devices _devices, HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===deviceManageAjax logged ,the action value is : {}", _dto.toString());
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                boolean ObjExists = devicesService.checkDevicesExists(_devices.getDevicename())
//                        ||devicesService.checkDevicesExists(_devices.getDevicenameCN())
//                        ||devicesService.checkDevicesExists(_devices.getDevicenameEN())
                        ;
                if (ObjExists) {
                    log.info("===deviceManageAjax logged ,添加设备已存在 : {}");
                    rtnData.put("error", "ERROR_DevicesExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "添加设备已存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加设备");
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, _devices, "添加设备", "");
                    devicesService.saveDevices(_devices);
                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除了设备", "");
                boolean b = devicesService.deleteDevicesById(_dto.sid);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除设备");
                } else {
                    rtnData.put("error", "ERROR_UserNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "设备种类不存在，删除失败");
                }
                break;
            case "update":
//                DeviceKind obj = new DeviceKind();
//                Long id = obj.getId();
//                obj.setDeviceKindName(name);
//                obj.setDeviceKindNameEN(name_en);
//                obj.setDeviceKindNameCN(name_cn);
////                    Devices.setId(id);
////                    Devices.setDevicesName(Devices_name);
////                    Devices.setDevicesNameCN(Devices_name_cn);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _devices, "更新了设备", "");
                devicesService.saveDevices(_devices);
                rtnData.put("success1", "success!");
                break;
            default:

                break;
        }
//        return "";
        mapper.writeValue(response.getWriter(), rtnData);
    }


    @RequestMapping("device_type_ajax")
//    @ResponseBody
    public void deviceTypeManageAjax(@ModelAttribute("dto") BaseDTO _dto, DeviceType _obj, HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===deviceTypeManageAjax logged ,the action value is : {}", _dto.toString());

        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                String name = request.getParameter("device_type_name");
                String name_cn = request.getParameter("device_type_name_cn");
                String name_en = request.getParameter("device_type_name_en");
                boolean ObjExists = deviceTypeService.checkDeviceTypeExists(name)
                        ||deviceTypeService.checkDeviceTypeExists(name_cn)
                        ||deviceTypeService.checkDeviceTypeExists(name_en);
                if (ObjExists) {
                    log.info("===deviceTypeManageAjax logged ,添加设备种类已存在 : {}");
                    rtnData.put("error", "ERROR_DevicesExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "添加设备种类已存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加设备种类");
                    DeviceType obj = new DeviceType();
                    obj.setDeviceTypeName(name);
                    obj.setDeviceTypeNameEN(name_en);
                    obj.setDeviceTypeNameCN(name_cn);
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, obj, "添加设备种类", "");
                    deviceTypeService.saveDeviceType(obj);
                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除了设备种类", "");
                boolean b = deviceTypeService.deleteDeviceTypeById(_dto.sid);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除设备种类");
                } else {
                    rtnData.put("error", "ERROR_UserNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "设备种类不存在，删除失败");
                }
                break;
            case "update":
//                DeviceType obj = new DeviceType();
//                Long id = obj.getId();
//                obj.setDeviceTypeName(name);
//                obj.setDeviceTypeNameEN(name_en);
//                obj.setDeviceTypeNameCN(name_cn);
////                    Devices.setId(id);
////                    Devices.setDevicesName(Devices_name);
////                    Devices.setDevicesNameCN(Devices_name_cn);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _obj, "更新了设备种类", "");
                deviceTypeService.saveDeviceType(_obj);
                rtnData.put("success1", "success!");
                break;
            default:

                break;
        }
//        return "";
        mapper.writeValue(response.getWriter(), rtnData);
    }


    @RequestMapping("device_kind_ajax")
    public void deviceKindManageAjax(@ModelAttribute("dto") BaseDTO _dto, DeviceKind _obj, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===deviceKindManageAjax logged ,the action value is : {}", _dto.toString());
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                String name = request.getParameter("device_kind_name");
                String name_cn = request.getParameter("device_kind_name_cn");
                String name_en = request.getParameter("device_kind_name_en");
                boolean ObjExists = deviceKindService.checkDeviceKindExists(name)
                        || deviceKindService.checkDeviceKindExists(name_cn)
                        || deviceKindService.checkDeviceKindExists(name_en);
                if (ObjExists) {
                    log.info("===deviceKindManageAjax logged ,添加设备性质已存在 : {}");
                    rtnData.put("error", "ERROR_DevicesExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "添加设备性质已存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加设备性质");
                    DeviceKind obj = new DeviceKind();
                    obj.setDeviceKindName(name);
                    obj.setDeviceKindNameEN(name_en);
                    obj.setDeviceKindNameCN(name_cn);
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, obj, "添加设备性质", "");
                    deviceKindService.saveDeviceKind(obj);
                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除了设备性质", "");
                boolean b = deviceKindService.deleteDeviceKindById(_dto.sid);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除设备性质");
                } else {
                    rtnData.put("error", "ERROR_UserNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "设备性质不存在，删除失败");
                }
                break;
            case "update":
//                DeviceKind obj = new DeviceKind();
//                Long id = obj.getId();
//                obj.setDeviceKindName(name);
//                obj.setDeviceKindNameEN(name_en);
//                obj.setDeviceKindNameCN(name_cn);
////                    Devices.setId(id);
////                    Devices.setDevicesName(Devices_name);
////                    Devices.setDevicesNameCN(Devices_name_cn);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _obj, "更新了设备性质", "");
                deviceKindService.saveDeviceKind(_obj);
                rtnData.put("success1", "success!");
                break;
            default:

                break;
        }
//        return "";
        mapper.writeValue(response.getWriter(), rtnData);
    }
}
