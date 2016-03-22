package net.liuxuan.SprKi.repository.security;

import net.liuxuan.SprKi.entity.security.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************<br/>
 * 源文件名:  net.liuxuan.SprKi.repository.security.AuthoritiesRepository<br/>
 * 功能: 	<br/>
 * 版本:	@version 1.0	  <br/>
 * 编制日期: 2016/3/22 10:42 	<br/>
 * 修改历史: (主要历史变动原因及说明)	<br/>
 * YYYY-MM-DD |    Author      |	 Change Description	<br/>
 * 2016/3/22  |    Moses       |     Created <br/>
 */
public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
}
