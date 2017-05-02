package net.liuxuan.SprKi.service.labthink;


import net.liuxuan.SprKi.entity.labthink.DeviceKind;
import java.util.List;


/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.labthink.DeviceKindService
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/31 08:50
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-31  |    Moses        |     Created
*/
public interface DeviceKindService {
    /**
     * Gets all device kind.
     *
     * @return the all device kind
     */
    List<DeviceKind> getAllDeviceKind();

    /**
     * Check device kind exists boolean.
     *
     * @param deviceKindName the device kind name
     * @return the boolean
     */
    boolean checkDeviceKindExists(String deviceKindName);

    List<DeviceKind> getDeviceKindsByName(String deviceKindName);

    /**
     * Save device kind.
     *
     * @param obj the obj
     */
    void saveDeviceKind(DeviceKind obj);

    /**
     * Delete device kind by id boolean.
     *
     * @param sid the sid
     * @return the boolean
     */
    boolean deleteDeviceKindById(String sid);

    /**
     * Gets device kind by id.
     *
     * @param id the id
     * @return the device kind by id
     */
    DeviceKind getDeviceKindById(Long id);



}