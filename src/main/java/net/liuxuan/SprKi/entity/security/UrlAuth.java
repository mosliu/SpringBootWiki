package net.liuxuan.SprKi.entity.security;


import javax.persistence.*;
//import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.entity.security.UrlAuth
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/22 14:41
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-22  |    Moses        |     Created
*/
@Getter
@Setter
@NoArgsConstructor
@Entity  //实体类
@Table(name = "Sprki_UrlAuth")
public class UrlAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false,length = 200)
    String urlAuthName;

    @Column(nullable = false,length = 200)
    String urlAuthNameCN;

    @Column(nullable = false,length = 200)
    String comment;

    @ManyToMany(mappedBy="urlAuths")
    //只需要设置mappedBy="games"表明Game实体是关系被维护端就可以了
    //级联保存、级联删除等之类的属性在多对多关系中是不需要设置
    //不能说删了游戏,把玩家也删掉,玩家还可以玩其他的游戏
    private Set<Role> roles = new HashSet<Role>();

    @Column(name = "disabled", nullable = false)
    boolean disabled=false;
}