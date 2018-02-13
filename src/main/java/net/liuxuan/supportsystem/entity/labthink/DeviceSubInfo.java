package net.liuxuan.supportsystem.entity.labthink;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.entity.labthink.DeviceSubInfo
* 功能:
* 版本:	@version 1.0
* 编制日期: 2018/02/13 09:33
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2018-02-13  |    Moses        |     Created
*/
@Data
@NoArgsConstructor
@Entity  //实体类
@Table(name = "Sprki_labthink_device_sub_info")
public class DeviceSubInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false,length = 200)
    private String deviceSubInfoName;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name="relativeDevice")
    private Devices relativeDevice;

    @Column(nullable = false,length = 200)
    private String comment;

    @Column(name = "disabled", nullable = false)
    private boolean disabled=false;
}