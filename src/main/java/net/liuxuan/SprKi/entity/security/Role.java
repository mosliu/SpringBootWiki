package net.liuxuan.SprKi.entity.security;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.entity.security.Role
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/20 11:49
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-20  |    Moses        |     Created
*/
@Data
@NoArgsConstructor
@Entity  //实体类
@Table(name = "Sprki_Role")
public class Role {

    @Id
    @NotNull
    @Column(nullable = false,length = 200)
    String rolename;

    @Column(nullable = false,length = 200)
    String roleDescribe;

    @Column(nullable = false,length = 200)
    String comment;

    @Column(name = "disabled", nullable = false)
    boolean disabled=false;

    public void setRolename(String rolename) {
        if(StringUtils.isBlank(rolename)){
            return;
        }
        rolename = rolename.toUpperCase();
        if(!rolename.startsWith("ROLE_")){
            rolename="ROLE_"+rolename;
        }
        this.rolename = rolename;
    }
}