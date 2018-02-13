package net.liuxuan.supportsystem.entity.labthink;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.labthink.Devices
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/7 15:20
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/7  |    Moses       |     Created
 */
@Data
@Entity  //实体类
@Table(name = "Sprki_Labthink_Devices")
public class Devices {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false,length = 100)
    String devicename;
    @Column(nullable = false,length = 100)
    String devicenameCN;
    @Column(nullable = false,length = 100)
    String devicenameEN;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="device_type")
    DeviceType deviceType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="device_kind")
    DeviceKind deviceKind;

}
