package net.liuxuan.supportsystem.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;

import javax.persistence.*;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.entity.SliderPics
* 功能: 首页轮播图片
* 版本:	@version 1.0
* 编制日期: 2017/03/27 13:42
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-27  |    Moses        |     Created
*/
@Data
@NoArgsConstructor
@Entity  //实体类
@Table(name = "Sprki_SliderPics")
@IgnoreSizeOf
public class SliderPics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false,length = 200)
    private String sliderPicsName;

    @Column(nullable = false,length = 200)
    private String sliderPicsNameCN;

    @Column(nullable = false,length = 200)
    private String comment;

    @Column(nullable = false,length = 400)
    private String url;

    @Column(name = "disabled", nullable = false)
    private boolean disabled=false;
}