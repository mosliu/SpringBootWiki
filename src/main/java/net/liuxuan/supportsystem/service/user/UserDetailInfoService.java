package net.liuxuan.supportsystem.service.user;


import net.liuxuan.supportsystem.entity.security.DbUser;
import net.liuxuan.supportsystem.entity.user.UserDetailInfo;

import java.lang.reflect.InvocationTargetException;
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
    int saveUserDetailInfo(UserDetailInfo userDetailInfo) throws InvocationTargetException, IllegalAccessException;


    DbUser findDbUserByUsername(String username);

    /**
     * Find user detail info by id user detail info.
     *
     * @param id the id
     * @return the user detail info
     */
    UserDetailInfo findUserDetailInfoById(Long id);

    /**
     * Find user detail info by users user detail info.
     *
     * @param dbUser the users
     * @return the user detail info
     */
    UserDetailInfo findUserDetailInfoByUsers(DbUser dbUser);

    UserDetailInfo findUserDetailInfoByUsername(String username);

    /**
     * Delete user detail info by id.
     *
     * @param id the id
     */
    void deleteUserDetailInfoById(Long id);


    /**
     * Delete users by username boolean.
     *
     * @param sid the sid
     * @return the boolean true for success
     */
    boolean deleteUsersByUsername(String sid);

    /**
     * List all users list.
     *
     * @return the list
     */
    List<DbUser> listAllUsers();


    List<UserDetailInfo> listAllUserDetailInfos();

    /**
     * Check users exists boolean.
     *
     * @param u the u
     * @return the boolean
     */
    boolean checkUsersExists(DbUser u);

    /**
     * Check users exists boolean.
     *
     * @param userDetailInfo the user detail info
     * @return the boolean
     */
    boolean checkUsersExists(UserDetailInfo userDetailInfo);

    /**
     * List auths list.
     *
     * @return the list
     */
//    List<String> listRoles();

    /**
     * Update auths boolean.
     *
     * @param userDetailInfo the user detail info
     * @param authArrays     the auth arrays
     * @param newauth        the newauth
     * @return the Map<String, Object>
     */
    Map<String, Object> updateRoles(UserDetailInfo userDetailInfo, String[] authArrays, String newauth);

    /**
     * Check avatar for all users
     *
     * @return
     */
    List<UserDetailInfo> checkAllUserDetailInfoAvatar();
}