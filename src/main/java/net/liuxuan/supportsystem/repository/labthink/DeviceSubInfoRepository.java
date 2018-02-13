package net.liuxuan.supportsystem.repository.labthink;

import java.util.List;
import net.liuxuan.supportsystem.entity.labthink.DeviceSubInfo;
import net.liuxuan.supportsystem.entity.labthink.Devices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.repository.labthink.DeviceSubInfoRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2018/02/13 09:33
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2018-02-13  |    Moses        |     Created
*/

public interface DeviceSubInfoRepository extends JpaRepository<DeviceSubInfo, Long>, JpaSpecificationExecutor<DeviceSubInfo> {
    List<DeviceSubInfo> findByDeviceSubInfoName(String  name);

    List<DeviceSubInfo> findByDeviceSubInfoNameNot(String  NotName);
    List<DeviceSubInfo> findByDisabledFalse();

    List<DeviceSubInfo> findByRelativeDeviceAndDisabledFalse(Devices d);
    List<DeviceSubInfo> findByDeviceSubInfoNameNotOrderByDeviceSubInfoName(String roleNotName);

}


