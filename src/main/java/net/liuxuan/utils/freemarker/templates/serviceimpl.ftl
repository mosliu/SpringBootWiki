package net.liuxuan.SprKi.service${subpackage};


import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.liuxuan.SprKi.repository${subpackage}.${model_name}Repository;
import net.liuxuan.SprKi.entity${subpackage}.${model_name};

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service${subpackage}.${model_name}ServiceImpl
* 功能:
* 版本:	@version 1.0
* 编制日期: ${date?string("yyyy/MM/dd HH:mm")}
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* ${date?string("yyyy-MM-dd")}  |    ${author}        |     Created
*/
@Service
@Transactional
public class ${model_name}ServiceImpl implements ${model_name}Service{

    private static Logger log = LoggerFactory.getLogger(${model_name}ServiceImpl.class);

    @Autowired
    ${model_name}Repository ${model_name_firstSmall}Repository;

    @Override
    public void save${model_name}(${model_name} ${model_name_firstSmall}){
        ${model_name_firstSmall}Repository.save(${model_name_firstSmall});
    }

    @Override
    public ${model_name} find${model_name}ById(Long id){
        ${model_name} ${model_name_firstSmall} = ${model_name_firstSmall}Repository.findOne(id);
        return ${model_name_firstSmall};
    }

    @Override
    public boolean delete${model_name}ById(Long id){
        ${model_name} ${model_name_firstSmall} = ${model_name_firstSmall}Repository.getOne(id);
        if (${model_name_firstSmall} != null) {
            ${model_name_firstSmall}.setDisabled(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean check${model_name}Exists(String ${model_name_firstSmall}name){
        List<${model_name}> list = ${model_name_firstSmall}Repository.findBy${model_name_firstSmall}name(${model_name_firstSmall}name);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public List<${model_name}> getAll${model_name}() {
        return ${model_name_firstSmall}Repository.findByDisabledFalse();
    }

}