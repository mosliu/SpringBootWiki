package net.liuxuan.SprKi.service.labthink;

import java.util.List;

import net.liuxuan.SprKi.entity.labthink.Devices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import net.liuxuan.SprKi.repository.labthink.DeviceSubInfoRepository;
import net.liuxuan.SprKi.entity.labthink.DeviceSubInfo;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.labthink.DeviceSubInfoServiceImpl
* 功能:
* 版本:	@version 1.0
* 编制日期: 2018/02/13 09:33
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2018-02-13  |    Moses        |     Created
*/
@Service
@Transactional
public class DeviceSubInfoServiceImpl implements DeviceSubInfoService{

    private static Logger log = LoggerFactory.getLogger(DeviceSubInfoServiceImpl.class);

    @Autowired
    DeviceSubInfoRepository deviceSubInfoRepository;

    @Override
    @CacheEvict(cacheNames = "deviceSubInfo", key = "'deviceSubInfo_list'")
    public void saveDeviceSubInfo(DeviceSubInfo deviceSubInfo){
        deviceSubInfoRepository.save(deviceSubInfo);
    }

    @Override
    @Cacheable(cacheNames = "deviceSubInfo", key = "#id")
    public DeviceSubInfo findDeviceSubInfoById(Long id){
        DeviceSubInfo deviceSubInfo = deviceSubInfoRepository.findOne(id);
        return deviceSubInfo;
    }

    @Override
    @CacheEvict(cacheNames = "deviceSubInfo", allEntries = true)
    public boolean deleteDeviceSubInfoById(Long id){
        DeviceSubInfo deviceSubInfo = deviceSubInfoRepository.getOne(id);
        if (deviceSubInfo != null) {
            deviceSubInfo.setDisabled(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkDeviceSubInfoExists(String deviceSubInfoname){
        List<DeviceSubInfo> list = getDeviceSubInfoByName(deviceSubInfoname);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Cacheable(cacheNames = "deviceSubInfo", key = "#deviceSubInfoname")
    public List<DeviceSubInfo> getDeviceSubInfoByName(String deviceSubInfoname) {
        return deviceSubInfoRepository.findByDeviceSubInfoName(deviceSubInfoname);
    }

    @Override
     @Cacheable(cacheNames = "deviceSubInfo", key = "'deviceSubInfo_list'")
    public List<DeviceSubInfo> getAllDeviceSubInfo() {
        return deviceSubInfoRepository.findByDisabledFalse();
    }

    @Override
    public List<DeviceSubInfo> getDeviceSubInfoByRelativeDevice(Devices d) {
        return deviceSubInfoRepository.findByRelativeDeviceAndDisabledFalse(d);
    }

}