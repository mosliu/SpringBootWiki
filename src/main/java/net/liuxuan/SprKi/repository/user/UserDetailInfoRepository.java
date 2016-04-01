package net.liuxuan.SprKi.repository.user;

import net.liuxuan.SprKi.entity.security.Users;
import net.liuxuan.SprKi.entity.user.UserDetailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.repository.user.UserDetailInfoRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2016/03/28 09:54
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2016-03-28  |    Moses        |     Created
*/

public interface UserDetailInfoRepository extends JpaRepository<UserDetailInfo, Long>, JpaSpecificationExecutor<UserDetailInfo> {
    UserDetailInfo findByUsers(Users users);
}


