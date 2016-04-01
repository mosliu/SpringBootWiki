package net.liuxuan.SprKi.entity${subpackage};


import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.entity${subpackage}.${model_name}
* 功能:
* 版本:	@version 1.0
* 编制日期: ${date?string("yyyy/MM/dd HH:mm")}
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* ${date?string("yyyy-MM-dd")}  |    ${author}        |     Created
*/
@Data
@NoArgsConstructor
@Entity  //实体类
@Table(name = "${table_name}")
public class ${model_name} {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false,length = 80)
    String ${model_name_firstSmall}Name;

    @Column(nullable = false,length = 80)
    String ${model_name_firstSmall}NameCN;

    @Column(name = "disabled", nullable = false)
    boolean disabled=true;
}