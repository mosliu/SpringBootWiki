package net.liuxuan.SprKi.service.labthink;


import net.liuxuan.SprKi.entity.labthink.DeviceSubInfo;
import net.liuxuan.SprKi.entity.labthink.Devices;

import java.util.List;


/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.labthink.DeviceSubInfoService
* 功能:
* 版本:	@version 1.0
* 编制日期: 2018/02/13 09:33
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2018-02-13  |    Moses        |     Created
*/
public interface DeviceSubInfoService {
    void saveDeviceSubInfo(DeviceSubInfo deviceSubInfo);

    DeviceSubInfo findDeviceSubInfoById(Long id);

    boolean deleteDeviceSubInfoById(Long id);

    boolean checkDeviceSubInfoExists(String deviceSubInfoname);

    List<DeviceSubInfo> getDeviceSubInfoByName(String deviceSubInfoname);

    List<DeviceSubInfo> getAllDeviceSubInfo();

    List<DeviceSubInfo> getDeviceSubInfoByRelativeDevice(Devices d);

}