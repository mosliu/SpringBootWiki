package net.liuxuan.SprKi.entity.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.security.Authorities
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/17 13:55
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/17 |    Moses       |     Created
 */
@Entity  //实体类
//@Data
@Table(name = "Sprki_Authorities")
public class Authorities implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    // optional=true：可选，表示此对象可以没有，可以为null；false表示必须存在
//    @JsonBackReference
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true,fetch = FetchType.LAZY)
    @JoinColumn(name="username")
    private DbUser username;



    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true,fetch = FetchType.LAZY)
    @JoinColumn(name="rolename")
    private Role rolename;


    @Column(length = 30,nullable = false)
    private String authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DbUser getUsername() {
        return username;
    }

    public void setUsername(DbUser username) {
        this.username = username;
    }

    @Override
    public String getAuthority() {
        return rolename.getRolename();
    }

    /**
     * @deprecated
     *
     * 无用的方法，升级遗留
     * @param authority
     */
    public void setAuthority(String authority) {
        this.authority=authority;
        //this.rolename.setRolename(authority);
    }

    public Role getRolename() {
        return rolename;
    }

    public void setRolename(Role rolename) {
//        this.authority = rolename.getRolename();
        this.rolename = rolename;
    }
}
