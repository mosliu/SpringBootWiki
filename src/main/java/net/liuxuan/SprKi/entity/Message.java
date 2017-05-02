package net.liuxuan.SprKi.entity;


import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false,length = 200)
    String messageName;

    @Column(nullable = false,length = 200)
    String messageNameCN;

    @Column(nullable = false,length = 200)
    String comment;

    @Column(name = "disabled", nullable = false)
    boolean disabled=false;
}