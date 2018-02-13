package net.liuxuan.supportsystem.entity.labthink;

import lombok.Data;

import javax.persistence.*;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.labthink.DeviceType
 * 功能: 设备类型的实体类（如阻隔性设备、力值设备等）
 * 版本:	@version 1.0
 * 编制日期: 2016/3/7 15:37
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/7  |    Moses       |     Created
 */

@Data
@Entity  //实体类
@Table(name = "Sprki_Labthink_DeviceType")
public class DeviceType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    //类型名
    @Column(nullable = false,length = 100)
    protected String deviceTypeName;
    //类型名
    @Column(nullable = false,length = 100)
    protected String deviceTypeNameEN;
    //类型名
    @Column(nullable = false,length = 100)
    protected String deviceTypeNameCN;

    protected boolean enable=true;
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deviceType")
//    private Set<Devices> items = new HashSet<Devices>();
}
