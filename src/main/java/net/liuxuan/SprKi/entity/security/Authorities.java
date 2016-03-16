package net.liuxuan.SprKi.entity.security;

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
@Data
@Table(name = "Sprki_Authorities")
public class Authorities implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // optional=true：可选，表示此对象可以没有，可以为null；false表示必须存在
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true,fetch = FetchType.LAZY)
    @JoinColumn(name="username")
    private Users username;

    @Column(length = 30,nullable = false)
    private String authority;

}
