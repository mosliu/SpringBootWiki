package net.liuxuan.SprKi.service.labthink;

import net.liuxuan.SprKi.entity.labthink.Department;
import net.liuxuan.SprKi.entity.labthink.DeviceKind;
import net.liuxuan.SprKi.entity.labthink.DeviceType;
import net.liuxuan.SprKi.entity.labthink.Devices;
import net.liuxuan.SprKi.repository.labthink.DeviceKindRepository;
import net.liuxuan.SprKi.repository.labthink.DeviceTypeRepository;
import net.liuxuan.SprKi.repository.labthink.DevicesRepository;
import net.liuxuan.spring.constants.JPAConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.labthink.DevicesServiceImpl
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/4/14 16:20
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/4/14  |    Moses       |     Created
 */
@Service
@Transactional
public class DevicesServiceImpl implements DevicesService {

    /**
     * The Devices repository.
     */
    @Autowired
    DevicesRepository devicesRepository;

    /**
     * The Device kind repository.
     */
    @Autowired
    DeviceKindRepository deviceKindRepository;

    /**
     * The Device type repository.
     */
    @Autowired
    DeviceTypeRepository deviceTypeRepository;

    /**
     * Gets all devices.
     *
     * @return the all devices
     */
    @Override
    public List<Devices> getAllDevices() {
//        return devicesRepository.findByDevicenameNot(JPAConstants.DELETEDOBJECTSTR);
        return devicesRepository.findByDevicenameNotOrderByDevicename(JPAConstants.DELETEDOBJECTSTR);
    }

    /**
     * Check devices exists boolean.
     *
     * @param devicename the devicename
     * @return the boolean
     */
    @Override
    public boolean checkDevicesExists(String devicename) {
//        List<Devices> list = devicesRepository.findByDevicename(devicename);
        List<Devices> list = devicesRepository.findByDevicenameOrDevicenameENOrDevicenameCN(devicename,devicename,devicename);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Save devices.
     *
     * @param obj the devices
     */
    @Override
    public void saveDevices(Devices obj) {
        devicesRepository.save(obj);
    }

    /**
     * Delete devices by id boolean.
     *
     * @param sid the sid
     * @return the boolean
     */
    @Override
    public boolean deleteDevicesById(String sid) {
        if (NumberUtils.isNumber(sid)) {
            Long id = Long.parseLong(sid);
            Devices obj = devicesRepository.getOne(id);
            if (obj != null) {
                obj.setDevicename(JPAConstants.DELETEDOBJECTSTR);
                obj.setDevicenameCN(JPAConstants.DELETEDOBJECTSTR);
                obj.setDevicenameEN(JPAConstants.DELETEDOBJECTSTR);
//                obj.setDepartmentName(JPAConstants.DELETEDOBJECTSTR);
//                obj.setDepartmentNameCN(JPAConstants.DELETEDOBJECTSTR);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets devices by id.
     *
     * @param id the id
     * @return the devices by id
     */
    @Override
    public Devices getDevicesById(Long id) {
        Devices obj = devicesRepository.getOne(id);
        return obj;
    }




}
