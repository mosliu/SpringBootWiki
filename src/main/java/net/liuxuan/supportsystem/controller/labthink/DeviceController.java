package net.liuxuan.supportsystem.controller.labthink;

import com.google.gson.Gson;
import net.liuxuan.spring.helper.gson.EntityGsonHelper;
import net.liuxuan.spring.helper.gson.TargetStrategy;
import net.liuxuan.supportsystem.entity.labthink.DeviceKind;
import net.liuxuan.supportsystem.entity.labthink.DeviceSubInfo;
import net.liuxuan.supportsystem.entity.labthink.DeviceType;
import net.liuxuan.supportsystem.entity.labthink.Devices;
import net.liuxuan.supportsystem.repository.labthink.DeviceKindRepository;
import net.liuxuan.supportsystem.repository.labthink.DeviceTypeRepository;
import net.liuxuan.supportsystem.repository.labthink.DevicesRepository;
import net.liuxuan.supportsystem.service.labthink.DeviceSubInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************<br/>
 * 源文件名:  net.liuxuan.SprKi.controller.labthink.DeviceController<br/>
 * 功能: 	<br/>
 * 版本:	@version 1.0	  <br/>
 * 编制日期: 2016/3/7 16:37 	<br/>
 * 修改历史: (主要历史变动原因及说明)	<br/>
 * YYYY-MM-DD |    Author      |	 Change Description	<br/>
 * 2016/3/7  |    Moses       |     Created <br/>
 */
@Controller
public class DeviceController {
    private static Logger log = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private DevicesRepository devicesRepository;
    @Autowired
    private DeviceKindRepository deviceKindRepository;
    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    @Autowired
    private  DeviceSubInfoService deviceSubInfoService;

    @ResponseBody
    @RequestMapping("/api/deviceSubInfo")
    public String getDeviceSubInfo(HttpServletRequest request,
                                   HttpServletResponse response, long id, Exception ex) {
        Devices device = devicesRepository.findOne(id);
        if(device!=null){
            List<DeviceSubInfo> deviceSubInfoByRelativeDevice = deviceSubInfoService.getDeviceSubInfoByRelativeDevice(device);
            Gson j = EntityGsonHelper.goEntityWithCollection2Gson(DeviceSubInfo.class);
            String rtn = j.toJson(deviceSubInfoByRelativeDevice);
            return rtn;
        }else{
            return "[]";
        }
//        List<Devices> l = devicesRepository.findAll();
//
//        TargetStrategy ts_devicetype = new TargetStrategy(DeviceType.class);
//        //这里表示仅转换DeviceType中的id和devicetypename属性
//        ts_devicetype.setFields(new String[] {"id", "deviceTypeName"});
//        ts_devicetype.setReverse(true);
//
//        TargetStrategy ts_devicekind = new TargetStrategy(DeviceKind.class);
//        //这里表示仅转换DeviceKind中的id和DeviceKindname属性
//        ts_devicetype.setFields(new String[] {"id", "deviceKindName"});
//        ts_devicetype.setReverse(true);


//        Gson j = EntityGsonHelper.goGsonBuilder(Devices.class).setExclusionStrategies(ts_devicetype,ts_devicekind).create();

//        return "common/temp";
//        System.out.println(rtn);
//        return rtn;
    }

    @ResponseBody
    @RequestMapping("/api/devices")
    public String getDevices(HttpServletRequest request,
                             HttpServletResponse response, Object handler, Exception ex) {
        List<Devices> l = devicesRepository.findAll();

        TargetStrategy ts_devicetype = new TargetStrategy(DeviceType.class);
        //这里表示仅转换DeviceType中的id和devicetypename属性
        ts_devicetype.setFields(new String[] {"id", "deviceTypeName"});
        ts_devicetype.setReverse(true);

        TargetStrategy ts_devicekind = new TargetStrategy(DeviceKind.class);
        //这里表示仅转换DeviceKind中的id和DeviceKindname属性
        ts_devicekind.setFields(new String[] {"id", "deviceKindName"});
        ts_devicekind.setReverse(true);


//        Gson j = EntityGsonHelper.goGsonBuilder(Devices.class).setExclusionStrategies(ts_devicetype,ts_devicekind).create();
        Gson j = EntityGsonHelper.goEntityWithCollection2Gson(Devices.class);
        String rtn = j.toJson(l);
//        return "common/temp";
        System.out.println(rtn);
        return rtn;
    }

    @ResponseBody
    @RequestMapping("/api/devices1")
    public String getDevices1(HttpServletRequest request,
                             HttpServletResponse response, Object handler, Exception ex) {

        DeviceType dt = new DeviceType();
        dt.setDeviceTypeName("ddd");
        dt.setDeviceTypeNameEN("短短的");

        DeviceKind dk = new DeviceKind();
        dk.setDeviceKindName("ccc");
        dk.setDeviceKindNameEN("茶厂村");

        Devices ox = new Devices();
        ox.setDeviceKind(dk);
        ox.setDeviceType(dt);
        ox.setDevicename("氧透过");

        deviceTypeRepository.save(dt);
        deviceKindRepository.save(dk);
        devicesRepository.save(ox);


        return "";
    }
//    @RequestMapping("/devices")
//    public ModelAndView getDevices(HttpServletRequest request,
//                                    HttpServletResponse response, Object handler, Exception ex) {
//        ModelAndView model = new ModelAndView("common/temp");
//        model.getModel().put("status", response.getStatus());
//        model.getModel().put("error", ex.getMessage());
//        model.getModel().put("message", ex.getMessage());
//        model.getModel().put("title", ex.getMessage());
//        model.getModel().put("date", new Date());
////        return "common/temp";
//        return model;
//    }
}
