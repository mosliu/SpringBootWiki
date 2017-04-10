package net.liuxuan.SprKi.service.labthink;


import net.liuxuan.SprKi.entity.labthink.DeviceType;
import java.util.List;


/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.labthink.DeviceTypeService
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/31 08:41
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-31  |    Moses        |     Created
*/
public interface DeviceTypeService {
    /**
     * Gets all device type.
     *
     * @return the all device type
     */
    List<DeviceType> getAllDeviceType();

    /**
     * Check device type exists boolean.
     *
     * @param DeviceTypeName the device type name
     * @return the boolean
     */
    boolean checkDeviceTypeExists(String DeviceTypeName);

    /**
     * Save device type.
     *
     * @param obj the obj
     */
    void saveDeviceType(DeviceType obj);

    /**
     * Delete device type by id boolean.
     *
     * @param sid the sid
     * @return the boolean
     */
    boolean deleteDeviceTypeById(String sid);

    /**
     * Gets device type by id.
     *
     * @param id the id
     * @return the device type by id
     */
    DeviceType getDeviceTypeById(Long id);

}