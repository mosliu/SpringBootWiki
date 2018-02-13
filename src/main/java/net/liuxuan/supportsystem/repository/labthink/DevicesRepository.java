package net.liuxuan.supportsystem.repository.labthink;

import net.liuxuan.supportsystem.entity.labthink.Devices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.<br />
 * ***************************************************************************<br />
 * 源文件名:  net.liuxuan.SprKi.repository.labthink.DevicesRepository<br />
 * 功能:<br />
 * 版本:	@version 1.0<br />
 * 编制日期: 2016/3/7 16:01<br />
 * 修改历史: (主要历史变动原因及说明)<br />
 * YYYY-MM-DD |    Author      |	 Change Description<br/>
 * 2016/3/7  |    Moses       |     Created<br/>
 */
@Repository
public interface DevicesRepository extends JpaRepository<Devices, Long> , JpaSpecificationExecutor<Devices> {

    List<Devices> findByDevicenameOrDevicenameENOrDevicenameCN(String  devicename,String  devicenameen,String  devicenamecn);
    List<Devices> findByDevicename(String  devicename);

    List<Devices> findByDevicenameNot(String  notDevicename);
    List<Devices> findByDevicenameNotOrderByDevicename(String  notDevicename);

}
