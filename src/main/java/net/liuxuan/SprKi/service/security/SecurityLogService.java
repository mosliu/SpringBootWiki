package net.liuxuan.SprKi.service.security;


import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.liuxuan.SprKi.entity.security.SecurityLog;


/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.security.SecurityLogService
* 功能:
* 版本:	@version 1.0
* 编制日期: 2016/04/20 14:52
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2016-04-20  |    Moses        |     Created
*/
public interface SecurityLogService {
    public void saveSecurityLog(SecurityLog securityLog);

    public SecurityLog findSecurityLogById(Long id);

    public void deleteSecurityLogById(Long id);
}