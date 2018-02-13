package net.liuxuan.supportsystem.repository.user;

import net.liuxuan.supportsystem.entity.user.UserDetailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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
//    UserDetailInfo findByDbUser(DbUser dbUser);
    List<UserDetailInfo> getAllByDisabledOrderByDepartment(boolean disabled);

    @Query ("select max(udi.id) from UserDetailInfo udi")
    Long maxIndex();
}


