package net.liuxuan.SprKi.service.user;


import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.liuxuan.SprKi.entity.security.Authorities;
import net.liuxuan.SprKi.entity.security.DbUser;
import net.liuxuan.SprKi.entity.security.DbUser;
import net.liuxuan.SprKi.entity.user.UserDetailInfo;

import java.util.List;
import java.util.Map;


/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.user.UserDetailInfoService
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/03/28 09:54
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016-03-28  |    Moses        |     Created
 */
public interface UserDetailInfoService {
    /**
     * Save user detail info int.
     *
     * @param userDetailInfo the user detail info
     * @return the int 0 means success
     */
    public int saveUserDetailInfo(UserDetailInfo userDetailInfo);

    /**
     * Find user detail info by id user detail info.
     *
     * @param id the id
     * @return the user detail info
     */
    public UserDetailInfo findUserDetailInfoById(Long id);

    /**
     * Find user detail info by users user detail info.
     *
     * @param dbUser the users
     * @return the user detail info
     */
    public UserDetailInfo findUserDetailInfoByUsers(DbUser dbUser);

    /**
     * Delete user detail info by id.
     *
     * @param id the id
     */
    public void deleteUserDetailInfoById(Long id);


    /**
     * Delete users by username boolean.
     *
     * @param sid the sid
     * @return the boolean true for success
     */
    public boolean deleteUsersByUsername(String sid);

    /**
     * List all users list.
     *
     * @return the list
     */
    public List<DbUser> listAllUsers();


    /**
     * Check users exists boolean.
     *
     * @param u the u
     * @return the boolean
     */
    public boolean checkUsersExists(DbUser u);

    /**
     * Check users exists boolean.
     *
     * @param userDetailInfo the user detail info
     * @return the boolean
     */
    public boolean checkUsersExists(UserDetailInfo userDetailInfo);

    /**
     * List auths list.
     *
     * @return the list
     */
    List<String> listAuths();

    /**
     * Update auths boolean.
     *
     * @param userDetailInfo the user detail info
     * @param authArrays     the auth arrays
     * @param newauth        the newauth
     * @return the Map<String, Object>
     */
    Map<String, Object> updateAuths(UserDetailInfo userDetailInfo, String[] authArrays, String newauth);
}