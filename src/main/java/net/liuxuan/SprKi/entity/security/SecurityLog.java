package net.liuxuan.SprKi.entity.security;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.Field;

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
    private Long id;

    /**
     * 访问者
     */
    @Column(length = 30)
    private String username;
//    @JsonIgnore
//    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true,fetch = FetchType.LAZY)
//    @JoinColumn(name="username")
//    private Users username;


    /**
     * 访问的IP
     */
    @Column(length = 40)
    String logIp;

    /**
     * 访问的地址
     */
    @Column(length = 100)
    String logUrl;

    /**
     * 操作时间
     */
    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    Date logTime; // 问题的时间

    /**
     * 动作
     */
    @Column(length = 40)
    String action;

    /**
     * 日志信息
     */
    @Lob
    @Field
    @Column(columnDefinition = "longtext", nullable = true)
//    @Basic(fetch = FetchType.LAZY)
    String logInfo;

    /**
     * 日志信息
     */
    @Column(length = 80)
    String logInfoType;


    /**
     * 日志等级
     */
    @Column(length = 20)
    String logLevel;

    @Column(length = 40)
    String operation;

    /**
     * 访问的地址 提供给可能能访问的日志展示用
     */
    @Column(length = 100)
    String url;

}