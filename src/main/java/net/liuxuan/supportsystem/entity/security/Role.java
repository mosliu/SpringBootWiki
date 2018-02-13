package net.liuxuan.supportsystem.entity.security;


import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

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
@IgnoreSizeOf
public class Role {

    @Id
    @NotNull
    @Column(nullable = false, length = 200)
    String rolename;

    @Column(nullable = false, length = 200)
    String roleDescribe;

    @Column(nullable = false, length = 200)
    String comment;

    @Column(name = "disabled", nullable = false)
    boolean disabled = false;

    //关系维护端，负责多对多关系的绑定和解除
    //@JoinTable注解的name属性指定关联表的名字，joinColumns指定外键的名字，关联到关系维护端(Player)
    //inverseJoinColumns指定外键的名字，要关联的关系被维护端(UrlAuth)
    //其实可以不使用@JoinTable注解，默认生成的关联表名称为主表表名+下划线+从表表名，
    //关联到主表的外键名：主表名+下划线+主表中的主键列名,即player_id
    //关联到从表的外键名：主表中用于关联的属性名+下划线+从表的主键列名,即game_id
    //主表就是关系维护端对应的表，从表就是关系被维护端对应的表
    @ManyToMany
    @JoinTable(name = "sprki_role_urlauth", joinColumns = @JoinColumn(name = "role_rolename"),
            inverseJoinColumns = @JoinColumn(name = "urlauth_id"))
    private Set<UrlAuth> urlAuths = new HashSet<UrlAuth>();


    public void setRolename(String rolename) {
        if (StringUtils.isBlank(rolename)) {
            return;
        }
        rolename = rolename.toUpperCase();
        if (!rolename.startsWith("ROLE_")) {
            rolename = "ROLE_" + rolename;
        }
        this.rolename = rolename;
    }
}