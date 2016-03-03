package net.liuxuan.SprKi.repository.security;

import net.liuxuan.SprKi.entity.security.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.repository.security.UsersRepository
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/17 13:28
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/17 |    Moses       |     Created
 */
@Transactional
public interface UsersRepository extends JpaRepository<Users, String> {
//    @Modifying
//    @Query("update Users m set m.name=?1 where m.id=?2")
//    public void update(String bannerName, Long id);

//    @Query("select DISTINCT(u.username) from ActiveWebSocketUser u where u.username != ?#{principal?.username}")
//    List<String> findAllActiveUsers();

}
