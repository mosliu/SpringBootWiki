package net.liuxuan.SprKi.entity;

import lombok.Data;
import net.liuxuan.SprKi.entity.labthink.Devices;
import net.liuxuan.SprKi.entity.security.Authorities;
import net.liuxuan.SprKi.entity.security.Users;

import javax.persistence.*;
import javax.persistence.spi.ClassTransformer;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.CMSContent
 * 功能: Entity for Information CMSContent
 * 版本:	@version 1.0
 * 编制日期: 2016/3/3 8:46
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/3  |    Moses       |     Created
 */
@Data
@Entity  //实体类
@Table(name = "Sprki_CMS_Content")
@Inheritance(strategy = InheritanceType.JOINED)
public class CMSContent {
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    protected Long id;

    /**
     * The Category.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    protected CMSCategory category; // 栏目


    /**
     * The Publish date.
     */
    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date publishDate; // 发布日期

    /**
     * The Last update date.
     */
    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastUpdateDate; // 最后更新时间

    /**
     * The Is copied.
     */
    protected boolean is_copied=false; ////是否转载

    /**
     * The Source.
     */
    @Column(length = 50)
    protected String source=""; // 来源名称
    /**
     * The Source url.
     */
    @Column(length = 200)
    protected String sourceUrl=""; // 来源url

    /**
     * The Author.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    protected Users author; // 作者

    /**
     * The Last update user.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    protected Users lastUpdateUser;

    /**
     * 主标题
     */
    @Column(length = 200,nullable = false)
    protected String title; // 主标题
    /**
     * 副标题
     */
    @Column(length = 200)
    protected String subtitle; // 副标题
    /**
     * 完整标题,带html，可以赋不同颜色和样式？
     */
    @Column(length = 1000)
    protected String fullTitle; // 完整标题,带html，可以赋不同颜色和样式？

    /**
     * 简介
     */
    @Column(length = 1000)
    protected String description; // 简介

    /**
     * 正文
     */
    @Lob
    @Column(columnDefinition = "mediumtext", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    protected String infoText =""; // 正文


    /**
     * 浏览总数
     */
    @Column(length = 8)
    protected Integer clicks=0; // 浏览总数
    /**
     * 评论总数
     */
    @Column(length = 8)
    protected Integer commentsCount=0; //
    /**
     * The Diggs.顶
     */
    @Column(length = 8)
    protected Integer diggs=0; //
    /**
     * 得分
     */
    @Column(length = 8)
    protected Integer score=0; //

    /**
     * '状态：0、草稿 1、已发布 2、待审核'
     */
    protected Integer state=0; //

    /**
     * The Disabled.
     */
    @Column( nullable = false)
    protected boolean disabled=false; ////是否删除

    /**
     * The Tags.
     */
    @ManyToMany
    @JoinTable (
            name="Sprki_CMS_Contents_Tags",
            joinColumns = {@JoinColumn(name="Content_ID")},
            inverseJoinColumns = {@JoinColumn(name="Tags_ID")}
    )
    protected Set<CMSContentTags> tags;

    /**
     * The Meta keywords.
     */
    protected String metaKeywords; // 关键字


    /**
     * Publish time.
     */
    @PrePersist
    void publishTime() {
        this.publishDate = this.lastUpdateDate = new Date();
    }

    /**
     * Last update time.
     */
    @PreUpdate
    void lastUpdateTime() {
        this.lastUpdateDate = new Date();
    }



    

}
