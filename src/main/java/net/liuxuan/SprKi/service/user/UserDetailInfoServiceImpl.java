package net.liuxuan.SprKi.service.user;


import net.liuxuan.SprKi.entity.security.Authorities;
import net.liuxuan.SprKi.entity.security.Users;
import net.liuxuan.SprKi.entity.user.UserDetailInfo;
import net.liuxuan.SprKi.repository.security.AuthoritiesRepository;
import net.liuxuan.SprKi.repository.security.UsersRepository;
import net.liuxuan.SprKi.repository.user.UserDetailInfoRepository;
import net.liuxuan.spring.Helper.bean.BeanHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.user.UserDetailInfoServiceImpl
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/03/28 09:54
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016-03-28  |    Moses        |     Created
 */
@Service
@Transactional
public class UserDetailInfoServiceImpl implements UserDetailInfoService {

    private static Logger log = LoggerFactory.getLogger(UserDetailInfoServiceImpl.class);

    /**
     * The User detail info repository.
     */
    @Autowired
    UserDetailInfoRepository userDetailInfoRepository;
    /**
     * The Users repository.
     */
    @Autowired
    UsersRepository usersRepository;
    /**
     * The Authorities repository.
     */
    @Autowired
    AuthoritiesRepository authoritiesRepository;


    /**
     * Save or update users.
     *
     * @param user the user
     */
    public void saveOrUpdateUsers(Users user) {
        assertUsersNotNull(user);
        String uname = user.getUsername();
        Users u_saved;
        if (usersRepository.exists(uname)) {
            u_saved = usersRepository.findOne(uname);
            if(StringUtils.isNotBlank(user.getPassword())){
                u_saved.setPassword(user.getPassword());
            }
            u_saved.setEnabled(user.isEnabled());
            u_saved.setUsernameAlias(user.getUsernameAlias());
            //先把传入的bean的空项填上
            //然后赋值给持久化对象
            //TODO 误赋值了SET属性
//            try {
//                BeanHelper.CopyWhenSrcFieldNotNullBeanUtilsBean(u_saved, user);
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
        } else {
            //new
            u_saved = user;
            log.info("===saveOrUpdateUsers logged ,Saved a new User: {}",uname);
            usersRepository.save(u_saved);
        }
//        return u_saved;

    }

    /**
     * Assert users not null.
     *
     * @param user the user
     */
    public void assertUsersNotNull(Users user) {
        Assert.notNull(user, "传入的user不应该为空");
        String uname = user.getUsername();
        Assert.notNull(uname, "希望更新的user，用户名不应该为空");
        Assert.hasText(uname, "希望更新的user，用户名不应该为空");
    }

    public boolean checkUsersExists(Users u){
        assertUsersNotNull(u);
        return usersRepository.exists(u.getUsername());
    }

    public boolean checkUsersExists(UserDetailInfo userDetailInfo){
        Assert.notNull(userDetailInfo);
        return checkUsersExists(userDetailInfo.getUsers());
    }


            @Override
    public int saveUserDetailInfo(UserDetailInfo userDetailInfo) {
        Users u = userDetailInfo.getUsers();
        Assert.notNull(u, "传入的user不应该为空");
        saveOrUpdateUsers(u);
        //重新取持久化的对象（该对象的auth为有内容的）
        u = usersRepository.findOne(u.getUsername());
        userDetailInfo.setUsers(u);
//        usersRepository.save(u);

        Set<Authorities> auths = u.getAuths();
        log.info("===saveUserDetailInfo logged ,the auths.size is : {}",auths.size());
//        for (Authorities auth : auths) {
//            if(auth.getAuthority().equals("ROLE_USER")){
//
//            }
//        }

        if (auths.isEmpty()) {
            //new
            Authorities auth = new Authorities();
            auth.setUsername(u);
            auth.setAuthority("ROLE_USER");
            authoritiesRepository.save(auth);
            auths.add(auth);
            log.info("===saveUserDetailInfo logged ,Add auth [{}] to [{}]",auths.size(),u.getUsername());
        }


//        Authorities auth = new Authorities();
//        auth.setUsername(u);
//        auth.setAuthority("ROLE_USER");
//        authoritiesRepository.save(auth);
////        Set<Authorities> auths = new HashSet<Authorities>();
//        auths.add(auth);
//        u.setAuths(auths);
        UserDetailInfo saved_UserDetailInfo = userDetailInfoRepository.findByUsers(u);
        if (saved_UserDetailInfo == null) {
            userDetailInfoRepository.save(userDetailInfo);
//            saved_UserDetailInfo = userDetailInfo;
        } else {
            try {
                BeanHelper.CopyWhenSrcFieldNotNullBeanUtilsBean(saved_UserDetailInfo, userDetailInfo);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    @Override
    public UserDetailInfo findUserDetailInfoByUsers(Users users) {
        Assert.notNull(users, "传入的users不能为空");
        Assert.notNull(users.getUsername(), "传入的users的用户名不能为空");
        Users u1 = usersRepository.findOne(users.getUsername());
        if (u1 == null) {
//            不存在用户！
            log.trace("请求用户 : {}不存在", users.getUsername());
            return null;
        }
        UserDetailInfo detailInfo = userDetailInfoRepository.findByUsers(u1);
        if (detailInfo == null) {
            log.trace("无user对应的UserInfo对象，建立");
            detailInfo = new UserDetailInfo();
            detailInfo.setUsers(u1);
            userDetailInfoRepository.save(detailInfo);
        }
        return detailInfo;
    }

    @Override
    public UserDetailInfo findUserDetailInfoById(Long id) {
        UserDetailInfo userDetailInfo = userDetailInfoRepository.findOne(id);
        return userDetailInfo;
    }

    @Override
    public void deleteUserDetailInfoById(Long id) {
        userDetailInfoRepository.findOne(id).setDisabled(true);
    }

    /**
     * Delete users by username boolean.
     *
     * @param sid the sid
     * @return the boolean
     */
    @Override
    public boolean deleteUsersByUsername(String sid) {
        if(usersRepository.exists(sid)){
            usersRepository.findOne(sid).setEnabled(false);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Users> listAllUsers() {
//        return usersRepository.findAll();
        return usersRepository.findByEnabled(true);
    }


}