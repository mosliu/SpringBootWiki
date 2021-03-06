package net.liuxuan.supportsystem.entity;

import lombok.Data;
import net.liuxuan.supportsystem.entity.security.DbUser;
import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

//import org.hibernate.search.annotations.Field;

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
@Indexed
@Entity  //实体类
@Table(name = "Sprki_CMS_Content")
@Inheritance(strategy = InheritanceType.JOINED)
@IgnoreSizeOf
public class CMSContent {
    /**
     * The Id.
     */
    @Id
    //@DocumentId:标明Car的id字段应该被用作Lucene索引中文档的ID，这几乎总是和数据库中实体的主键是同一个字段。
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    protected Long id;

    /**
     * The Category.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @IndexedEmbedded(depth = 1, prefix = "categoryBy_")
    @JoinColumn(name="category")
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
    @JoinColumn(name="author")
    protected DbUser author; // 作者

    /**
     * The Last update user.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="last_update_user")
    protected DbUser lastUpdateUser;

    /**
     * 主标题
     */
    @Field(store = Store.NO)
    @Column(length = 200,nullable = false)
    protected String title; // 主标题
    /**
     * 副标题
     */
    @Field(store = Store.NO)
    @Column(length = 200)
    protected String subtitle; // 副标题
    /**
     * 完整标题,带html，可以赋不同颜色和样式？
     */
    // You have to mark the fields you want to make searchable annotating them
    // with Field.
    // The parameter Store.NO ensures that the actual data will not be stored in
    // the index (mantaining the ability to search for it): Hibernate Search
    // will execute a Lucene query in order to find the database identifiers of
    // the entities matching the query and use these identifiers to retrieve
    // managed objects from the database.
    @Field(store = Store.NO)
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
    @Field
    @Column(columnDefinition = "longtext", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    protected String infoText =""; // 正文


    /**
     * 浏览总数
     */
    @Column(length = 8)
    protected Integer clicks=1; // 浏览总数
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
     * The Comments.
     */
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "content"

    )
    protected List<CMSComment> comments;
    /**
     * The Meta keywords.
     */
    @Field
    protected String metaKeywords; // 关键字



    /**
     * Publish time,publish author
     */
//    @PrePersist
//    void PrePersist() {
//        //Publish time.
////        System.out.println("!!!!!!!!!!!!!!!!!PrePersist");
//        this.publishDate = this.lastUpdateDate = new Date();
//
////
////        UserInfo ui = (UserInfo) SecurityContextHolder.getContext()
////                .getAuthentication().getPrincipal();
////        this.author = this.lastUpdateUser = ui.getUsers();
//
////
////        Assert.notNull(null);
////        this.author = this.lastUpdateUser = new Users();
//
//    }

    /**
     * Last update time.
     */
//    @PreUpdate
//    void PreUpdate() {
////        System.out.println("!!!!!!!!!!!!!!!!!PreUpdate");
//        this.lastUpdateDate = new Date();
////        UserInfo ui = (UserInfo) SecurityContextHolder.getContext()
////                .getAuthentication().getPrincipal();
////        this.lastUpdateUser = ui.getUsers();
//    }



    

}
