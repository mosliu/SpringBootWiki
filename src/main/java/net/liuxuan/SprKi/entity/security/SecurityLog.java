package net.liuxuan.SprKi.entity.security;


import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false,length = 1000)
    String info;

    @Column(nullable = false,length = 40)
    String logip;

    @Column(nullable = false,length = 40)
    String loguser;

    @Column(nullable = false,length = 40)
    String operation;

    @Column(name = "disabled", nullable = false)
    boolean disabled=true;
}