package net.liuxuan.SprKi.repository${subpackage};

import net.liuxuan.SprKi.entity${subpackage}.${model_name};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.repository${subpackage}.${model_name}Repository
* 功能:
* 版本:	@version 1.0
* 编制日期: ${date?string("yyyy/MM/dd HH:mm")}
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* ${date?string("yyyy-MM-dd")}  |    ${author}        |     Created
*/

public interface ${model_name}Repository extends JpaRepository<${model_name}, Long>, JpaSpecificationExecutor<${model_name}> {
}


