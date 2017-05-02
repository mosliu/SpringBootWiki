package net.liuxuan.SprKi.entity;


import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.ProjectProgress
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/03/27 14:59
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017-03-27  |    Moses        |     Created
 */
@Data
@NoArgsConstructor
@Entity  //实体类
@Table(name = "Sprki_ProjectProgress")
public class ProjectProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    /**
     * The Project progress name.
     */
    @Column(nullable = false,length = 200)
    String projectProgressName;

    /**
     * The Project progress.
     */
    @Column(nullable = false)
    Integer projectProgress;

    /**
     * The Comment.
     */
    @Column(nullable = true,length = 200)
    String comment;


    /**
     * The Disabled.
     */
    @Column(name = "disabled", nullable = false)
    boolean disabled=false;
}