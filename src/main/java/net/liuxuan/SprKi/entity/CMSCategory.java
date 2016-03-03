package net.liuxuan.SprKi.entity;

import javax.persistence.*;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.CMSCategory
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/3 13:23
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/3  |    Moses       |     Created
 */
@Entity  //实体类
@Table(name = "Sprki_CMS_Category")
public class CMSCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;//名称

    @Column(length = 50)
    private String englishName;//英文名

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private CMSCategory parentId;//父分类
    private String url;//地址
    private int pageSize;//每页数据
    @Column(name = "disabled", nullable = false)
    private boolean disabled=true;
}
