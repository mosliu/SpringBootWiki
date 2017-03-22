package net.liuxuan.SprKi.service${subpackage};


import net.liuxuan.SprKi.entity${subpackage}.${model_name};
import java.util.List;


/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service${subpackage}.${model_name}Service
* 功能:
* 版本:	@version 1.0
* 编制日期: ${date?string("yyyy/MM/dd HH:mm")}
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* ${date?string("yyyy-MM-dd")}  |    ${author}        |     Created
*/
public interface ${model_name}Service {
    void save${model_name}(${model_name} ${model_name_firstSmall});

    ${model_name} find${model_name}ById(Long id);

    boolean delete${model_name}ById(Long id);

    boolean check${model_name}Exists(String ${model_name_firstSmall}name);

    List<${model_name}> getAll${model_name}();

}