package net.liuxuan.supportsystem.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import net.liuxuan.supportsystem.entity.user.UserDetailInfo;

import javax.persistence.*;
import java.util.Date;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.Message
 * 功能: 实现短消息功能
 * 版本:	@version 1.0
 * 编制日期: 2017/04/22 09:48
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017-04-22  |    Moses        |     Created
 */
@Data
@NoArgsConstructor
@Entity  //实体类
@Table(name = "Sprki_Message")
public class Message {
    @Column(nullable = false, length = 200)
    protected String title;
    @Column(nullable = false, length = 1000)
    protected String content;
    @Column(nullable = true, length = 200)
    protected String comment;
    @Column(name = "deleted", nullable = false)
    protected boolean deleted = false;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fromUser")
    protected UserDetailInfo fromUser;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "toUser")
    protected UserDetailInfo toUser;
    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date sendTime; // 发布日期
    @Column(name = "sendIP")
    protected String sendIP;
    @Column(name = "status")
    protected String status; //状态，已发送？未读？已读？
    @Column(name = "messageType")
    protected String messageType;        // 消息类型，  发出的私信 ， 收到的私信， 系统消息
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ref")
    protected Message ref;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;
}