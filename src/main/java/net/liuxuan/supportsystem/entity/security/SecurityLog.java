package net.liuxuan.supportsystem.entity.security;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import java.util.Date;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.security.SecurityLog
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/04/20 14:52
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016-04-20  |    Moses        |     Created
 */
@Data
@NoArgsConstructor
@Entity  //实体类
@Table(name = "Sprki_Labthink_SecurityLog")
public class SecurityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    protected Long id;
    /**
     * 访问的IP
     */
    @Column(length = 40)
    protected String logIp;
//    @JsonIgnore
//    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true,fetch = FetchType.LAZY)
//    @JoinColumn(name="username")
//    private Users username;
    /**
     * 访问的地址
     */
    @Column(length = 100)
    protected String logUrl;
    /**
     * 操作时间
     */
    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date logTime; // 问题的时间
    /**
     * 动作
     */
    @Column(length = 40)
    protected String action;
    /**
     * 日志信息
     */
    @Lob
    @Field
    @Column(columnDefinition = "longtext", nullable = true)
//    @Basic(fetch = FetchType.LAZY)
    protected String logInfo;
    /**
     * 日志信息
     */
    @Column(length = 80)
    protected String logInfoType;
    /**
     * 日志等级
     */
    @Column(length = 20)
    protected String logLevel;
    @Column(length = 40)
    protected String operation;
    /**
     * 访问的地址 提供给可能能访问的日志展示用
     */
    @Column(length = 100)
    protected String url;
    /**
     * 访问者
     */
    @Column(length = 30)
    private String username;

}