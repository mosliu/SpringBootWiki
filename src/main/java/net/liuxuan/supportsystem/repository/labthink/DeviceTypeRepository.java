package net.liuxuan.supportsystem.repository.labthink;

import net.liuxuan.supportsystem.entity.labthink.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************<br/>
 * 源文件名:  net.liuxuan.SprKi.repository.labthink.DeviceTypeRepository<br/>
 * 功能: 	<br/>
 * 版本:	@version 1.0	  <br/>
 * 编制日期: 2016/3/7 16:24 	<br/>
 * 修改历史: (主要历史变动原因及说明)	<br/>
 * YYYY-MM-DD |    Author      |	 Change Description	<br/>
 * 2016/3/7  |    Moses       |     Created <br/>
 */
@Repository
public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long> {
    List<DeviceType> findByDeviceTypeNameOrDeviceTypeNameENOrDeviceTypeNameCN(String deviceTypeName,String deviceTypeNameEN,String deviceTypeNameCN);
    List<DeviceType> findByDeviceTypeName(String deviceTypeName);
    List<DeviceType> findByDeviceTypeNameEN(String deviceTypeName);
    List<DeviceType> findByDeviceTypeNameCN(String deviceTypeName);

    List<DeviceType> findByDeviceTypeNameNot(String notDeviceTypeName);
}
