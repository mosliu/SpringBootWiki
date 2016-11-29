package net.liuxuan.SprKi.entity.user;


import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.liuxuan.SprKi.entity.labthink.Department;
import net.liuxuan.SprKi.entity.security.Users;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.entity.user.UserDetailInfo
* 功能:
* 版本:	@version 1.0
* 编制日期: 2016/03/28 09:54
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2016-03-28  |    Moses        |     Created
*/
@Data
@NoArgsConstructor
@Entity  //实体类
@Table(name = "Sprki_Labthink_UserDetailInfo")
public class UserDetailInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    Users users;

    @Column(name = "disabled", nullable = false)
    boolean disabled=false;

    @Column(nullable = true ,length = 50)
    String firstName;

    @Column(nullable = true ,length = 50)
    String lastName;

    @Column(nullable = true ,length = 20)
    String gender;

    @Column(nullable = true ,length = 50)
    String email;
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    Department department;


}