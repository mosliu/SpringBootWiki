package net.liuxuan.SprKi.service.labthink;

import java.util.List;

import net.liuxuan.spring.constants.JPAConstants;
import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.liuxuan.SprKi.repository.labthink.DeviceTypeRepository;
import net.liuxuan.SprKi.entity.labthink.DeviceType;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.labthink.DeviceTypeServiceImpl
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/31 08:41
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-31  |    Moses        |     Created
*/
@Service
@Transactional
//@CacheDefaults(cacheName = "deviceType")
public class DeviceTypeServiceImpl implements DeviceTypeService{

    private static Logger log = LoggerFactory.getLogger(DeviceTypeServiceImpl.class);

    @Autowired
    DeviceTypeRepository deviceTypeRepository;

    /**
     * Save device type.
     *
     * @param obj the devices
     */
    @Override
//    @CachePut(cacheNames = "deviceType", key = "#obj.id")
    public DeviceType saveDeviceType(DeviceType obj) {
        return deviceTypeRepository.save(obj);
    }

    /**
     * Gets device type by id.
     *
     * @param id the id
     * @return the device type by id
     */
    @Override
    @Cacheable(cacheNames = "deviceType", key = "#id")
    public DeviceType getDeviceTypeById(Long id) {
        DeviceType obj = deviceTypeRepository.getOne(id);
        return obj;
    }

    /**
     * Delete device type by id boolean.
     *
     * @param sid the sid
     * @return the boolean
     */
    @Override
    public boolean deleteDeviceTypeById(String sid) {
        if (NumberUtils.isNumber(sid)) {
            Long id = Long.parseLong(sid);
            if (deleteDeviceTypeById(id)) return true;
        }
        return false;
    }

    @CacheEvict(cacheNames = "deviceType", key = "#id")
//    @CacheRemove
    public boolean deleteDeviceTypeById(Long id) {
        DeviceType obj = deviceTypeRepository.getOne(id);
        if (obj != null) {
            obj.setDeviceTypeName(JPAConstants.DELETEDOBJECTSTR);
            obj.setDeviceTypeNameCN(JPAConstants.DELETEDOBJECTSTR);
            obj.setDeviceTypeNameEN(JPAConstants.DELETEDOBJECTSTR);
//                obj.setDepartmentName(JPAConstants.DELETEDOBJECTSTR);
//                obj.setDepartmentNameCN(JPAConstants.DELETEDOBJECTSTR);
            return true;
        }
        return false;
    }

    /**
     * Check device type exists boolean.
     *
     * @param DeviceTypeName the device type name
     * @return the boolean
     */
    @Override
    public boolean checkDeviceTypeExists(String DeviceTypeName) {
        List<DeviceType> list = getDeviceTypesByName(DeviceTypeName);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Cacheable(cacheNames = "deviceType", key = "#DeviceTypeName")
//    @CacheResult
    public List<DeviceType> getDeviceTypesByName(String DeviceTypeName) {
        return deviceTypeRepository.findByDeviceTypeNameOrDeviceTypeNameENOrDeviceTypeNameCN(DeviceTypeName,DeviceTypeName,DeviceTypeName);
    }

    /**
     * Gets all device type.
     *
     * @return the all device type
     */
    @Override
    @Cacheable(cacheNames = "deviceType", key = "'deviceType_list'")
//    @CacheResult
    public List<DeviceType> getAllDeviceType() {
        return deviceTypeRepository.findByDeviceTypeNameNot(JPAConstants.DELETEDOBJECTSTR);
    }

}