package net.liuxuan.supportsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import net.liuxuan.supportsystem.entity.user.UserDetailInfo;
import net.liuxuan.spring.Helper.SystemHelper;

import javax.persistence.*;
import java.util.Date;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.CMSComment <br/>
 * 功能: 评论实体<br/>
 * 版本:	@version 1.0 <br/>
 * 编制日期: 2016/3/11 13:59 <br/>
 * 修改历史: (主要历史变动原因及说明) <br/>
 * YYYY-MM-DD |    Author      |	 Change Description <br/>
 * 2016/3/11  |    Moses       |     Created <br/>
 */
@Data
@Entity  //实体类
@Table(name = "Sprki_CMS_Comment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CMSComment {
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    protected Long id;

    /**
     * The Disabled.
     */
    @Column(nullable = false)
    protected boolean disabled = false; ////是否删除

    /**
     * The Publish date.
     */
    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date publishDate; // 发布日期

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author")
    @JsonIgnore
    private UserDetailInfo author;

    @Column(length = 40, nullable = true)
    private String commentIP;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "parent")
    @JsonIgnore
    private CMSComment parent;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "cmsContent_id")
    @JsonIgnore
    private CMSContent content;
    /**
     * 评论类型
     */
    @Column(length = 20, nullable = true)
    private String CommentKind;
    @Column(length = 40, nullable = true)
    private String commentTitle;
    /**
     * 评论内容
     */
    @Column(columnDefinition = "text", nullable = false)
    private String commentContent;
    /**
     * 当前用户是否可修改，读取前应调用judgeCanEdit
     */
    @Transient
    private boolean canEdit = false;

    /**
     * Publish time.
     */
    @PrePersist
    void publishTime() {
        this.publishDate = new Date();
    }

    /**
     * 决定是否可修改，结果写入canEdit。
     *
     * @param isadmin 从调用者处获得是否admin，针对多条处理时会有加速作用
     */
    public void judgeCanEdit(boolean isadmin) {
        if (isadmin) {
            canEdit = true;
        } else {
            UserDetailInfo currentUserDetailInfo = SystemHelper.getCurrentUserDetailInfo();
            canEdit = (currentUserDetailInfo.getId() == author.getId());
        }
    }

    public boolean judgeCanEdit() {
        judgeCanEdit(SystemHelper.isCurrentUserAdmin());
        return canEdit;
    }
}
