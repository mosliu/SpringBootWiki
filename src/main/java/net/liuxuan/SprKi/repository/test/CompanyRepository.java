package net.liuxuan.SprKi.repository.test;

import net.liuxuan.SprKi.entity.test.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************<br/>
 * 源文件名:  net.liuxuan.SprKi.repository.test.CompanyRepository<br/>
 * 功能: 	<br/>
 * 版本:	@version 1.0	  <br/>
 * 编制日期: 2016/3/17 13:50 	<br/>
 * 修改历史: (主要历史变动原因及说明)	<br/>
 * YYYY-MM-DD |    Author      |	 Change Description	<br/>
 * 2016/3/17  |    Moses       |     Created <br/>
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByName(String name);

}
