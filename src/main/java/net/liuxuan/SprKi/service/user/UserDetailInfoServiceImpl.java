package net.liuxuan.SprKi.service.user;


import net.liuxuan.SprKi.entity.security.Authorities;
import net.liuxuan.SprKi.entity.security.DbUser;
import net.liuxuan.SprKi.entity.security.Role;
import net.liuxuan.SprKi.entity.user.UserDetailInfo;
import net.liuxuan.SprKi.repository.security.AuthoritiesRepository;
import net.liuxuan.SprKi.repository.security.UsersRepository;
import net.liuxuan.SprKi.repository.user.UserDetailInfoRepository;
import net.liuxuan.SprKi.service.security.RoleService;
import net.liuxuan.spring.Helper.bean.BeanHelper;
import net.liuxuan.utils.identicon.DrawUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
     * The Users repository.
     */
    @Autowired
    RoleService roleService;

    /**
     * The Authorities repository.
     */
    @Autowired
    AuthoritiesRepository authoritiesRepository;

    /**
     * The Store path.
     */
    @Value("${SprKi.avatar.storePath}")
    String storePath;
    /**
     * The Access url path.
     */
    @Value("${SprKi.avatar.accessUrlPath}")
    String accessUrlPath;

    /**
     * 输入的字符是否是汉字
     *
     * @param a char
     * @return boolean boolean
     */
    public static boolean isChinese(char a) {
        int v = (int) a;
        return (v >= 19968 && v <= 171941);
    }

    /**
     * Save or update users.
     *
     * @param user the user
     * @return the db user
     */
//    @CachePut(cacheNames = "dbUsers", key = "#user.username")
    public DbUser saveOrUpdateUsers(DbUser user) throws InvocationTargetException, IllegalAccessException {
        String uname = user.getUsername();
        DbUser u_saved;
        if (usersRepository.exists(uname)) {
            u_saved = usersRepository.findOne(uname);
            if (StringUtils.isBlank(user.getPassword()) || user.getPassword().length() < 4) {
                user.setPassword(null);
            }
            user.setEnabled(u_saved.isEnabled());
            //先把传入的bean的空项填上
            //然后赋值给持久化对象
            BeanHelper.CopyWhenSrcFieldNotNullBeanUtilsBean(u_saved, user);
            usersRepository.save(u_saved);
        } else {
            //new
            log.info("===saveOrUpdateUsers logged ,Saved a new User: {}", uname);
            u_saved  = usersRepository.save(user);
        }
        return u_saved;
    }

    /**
     * Assert users not null.
     *
     * @param user the user
     */
    public void assertUsersNotNull(DbUser user) {
        Assert.notNull(user, "传入的user不应该为空");
        String uname = user.getUsername();
        Assert.notNull(uname, "希望更新的user，用户名不应该为空");
        Assert.hasText(uname, "希望更新的user，用户名不应该为空");
    }

    public boolean checkUsersExists(DbUser u) {
        assertUsersNotNull(u);
        return usersRepository.exists(u.getUsername());
    }

    public boolean checkUsersExists(UserDetailInfo userDetailInfo) {
        Assert.notNull(userDetailInfo);
        return checkUsersExists(userDetailInfo.getDbUser());
    }

    @Override
//    @CachePut(cacheNames = "userDetailInfo", key = "#userDetailInfo.id")
    @CacheEvict(cacheNames = "userDetailInfo",allEntries = true)
    public int saveUserDetailInfo(UserDetailInfo userDetailInfo) throws InvocationTargetException, IllegalAccessException {
        DbUser u = userDetailInfo.getDbUser();


        if (userDetailInfo.getId() == null) {
            //新的
            u.setUserDetailInfo(userDetailInfo);
            userDetailInfo.setAvatar(createAvatarFile(userDetailInfo.getEmail()));
            log.info("===saveOrUpdateUsers logged ,Saved a new User: {}", u.getUsername());
            usersRepository.save(u);
            addBasicUserRole(u);
        } else {
            //更新现存
            UserDetailInfo saved_UserDetailInfo = userDetailInfoRepository.findOne(userDetailInfo.getId());
            DbUser user = userDetailInfo.getDbUser();
            //检查密码
            if (StringUtils.isBlank(user.getPassword()) || user.getPassword().length() < 4) {
                user.setPassword(null);
            }
            //检查设置enable属性
            user.setEnabled(saved_UserDetailInfo.getDbUser().isEnabled());


            BeanHelper.CopyWhenSrcFieldNotNullBeanUtilsBean(saved_UserDetailInfo.getDbUser(), user);
            BeanHelper.CopyWhenSrcFieldNotNullBeanUtilsBean(saved_UserDetailInfo, userDetailInfo);
        }

//        saveOrUpdateUsers(u);

        return 0;
    }

    private void addBasicUserRole(DbUser u) {
        Set<Authorities> auths = u.getAuths();
        log.info("===saveUserDetailInfo logged ,the auths.size is : {}", auths.size());
        if(auths == null){
            auths = new HashSet<Authorities>();
            u.setAuths(auths);
        }
        if (auths.isEmpty()) {
            //new
            Authorities auth = new Authorities();
            auth.setUsername(u);

//            Role userrole = roleRepository.findOne("ROLE_USER");
            Role userrole = roleService.findRoleById("ROLE_USER");

            auth.setRolename(userrole);
            authoritiesRepository.save(auth);
            auths.add(auth);
            log.info("===saveUserDetailInfo logged ,Add auth [{}] to [{}]", auths.size(), u.getUsername());
        }
    }

    @Override
//    @Cacheable(cacheNames = "userDetailInfo", key = "#dbUser.username")
    public UserDetailInfo findUserDetailInfoByUsers(DbUser dbUser) {
        Assert.notNull(dbUser, "传入的users不能为空");

        return findUserDetailInfoByUsername(dbUser.getUsername());
    }

    @Override
    @Cacheable(cacheNames = "userDetailInfo", key = "#username")
    public UserDetailInfo findUserDetailInfoByUsername(String username) {
        DbUser u1 = findDbUserByUsername(username);
        if (u1 == null) {
//            不存在用户！
            log.trace("请求用户 : {}不存在", username);
            return null;
        }
//        UserDetailInfo detailInfo = userDetailInfoRepository.findByDbUser(u1);
        UserDetailInfo detailInfo = u1.getUserDetailInfo();
        if (detailInfo == null) {
            log.trace("无user对应的UserInfo对象，建立");
            detailInfo = new UserDetailInfo();
            detailInfo.setDbUser(u1);
            userDetailInfoRepository.save(detailInfo);
        }
        return detailInfo;
    }

    @Override
    public DbUser findDbUserByUsername(String username) {
        Assert.notNull(username, "传入的用户名不能为空");
        DbUser u1 = usersRepository.findOne(username);
        return u1;
    }

    @Override
    @Cacheable(cacheNames = "userDetailInfo", key = "#id")
    public UserDetailInfo findUserDetailInfoById(Long id) {
        UserDetailInfo userDetailInfo = userDetailInfoRepository.findOne(id);
        return userDetailInfo;
    }

    @Override
    @CacheEvict(cacheNames = "userDetailInfo", key = "#id")
    public void deleteUserDetailInfoById(Long id) {
        userDetailInfoRepository.findOne(id).setDisabled(true);
    }

    /**
     * Delete users by username boolean.
     *
     * @param username the username
     * @return the boolean
     */
    @Override
    @CacheEvict(cacheNames = "dbUsers", key = "#username")
    public boolean deleteUsersByUsername(String username) {
        if (usersRepository.exists(username)) {
            DbUser one = usersRepository.findOne(username);
            one.setEnabled(false);
//            UserDetailInfo byDbUser = userDetailInfoRepository.findByDbUser(one);
            UserDetailInfo byDbUser = one.getUserDetailInfo();
            if (byDbUser != null) {
                byDbUser.setDisabled(true);
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    @Cacheable(cacheNames = "dbUsers", key = "'user_list'")
    public List<DbUser> listAllUsers() {
//        return usersRepository.findAll();
        return usersRepository.findByEnabled(true);
    }

//    @Override
//    @Cacheable(cacheNames = "role", key = "'list_allRoleNames'")
//    public List<String> listRoles() {
////        List<String> distinctAuthority = authoritiesRepository.findAllAuthorities();
////        List<String> distinctAuthority = authoritiesRepository.findAllAuthorities();
////        return distinctAuthority;
////        List<String> rtnl = roleRepository.findAllRoleNames();
//        List<String> rtnl = roleService.findAllRoleNames();
//        return rtnl;
////        return usersRepository.findAll();
//    }

    @Override
    @Cacheable(cacheNames = "userDetailInfo", key = "'userdetailinfo_list'")
    public List<UserDetailInfo> listAllUserDetailInfos() {
//        return userDetailInfoRepository.findAll();
        return userDetailInfoRepository.getAllByDisabledOrderByDepartment(false);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "dbUsers", key = "#userDetailInfo.dbUser.username"),
                    @CacheEvict(cacheNames = "userDetailInfo", key = "'userdetailinfo_list'"),
                    @CacheEvict(cacheNames = "userDetailInfo", key = "#userDetailInfo.dbUser.username")
//                    @CacheEvict(cacheNames = "userDetailInfo", allEntries = true)
            })
    public Map<String, Object> updateRoles(UserDetailInfo userDetailInfo, String[] authArrays, String newauth) {
        Map<String, Object> map = new HashMap<String, Object>();

        DbUser dbUser = usersRepository.findOne(userDetailInfo.getDbUser().getUsername());
        Set<Authorities> old_auths = dbUser.getAuths();
//        Set<Authorities> new_auths = new HashSet<Authorities>();
        Set<Authorities> toremove = new HashSet<Authorities>();

        //先比较新旧auths

        if (ArrayUtils.isEmpty(authArrays)) {
            //提交错误了啊。。
            map.put("error", "提交出错！请检查");
            return map;
        }


        for (Authorities old_auth : old_auths) {
            boolean remain = false;
            for (int i = 0; i < authArrays.length; i++) {
                String s_newauth = authArrays[i].toUpperCase();
                if (old_auth.getAuthority().toUpperCase().equals(s_newauth)) {
                    remain = true;
                    log.info("===用户【{}】 保持 权限【{}】 不变", dbUser.getUsername(), s_newauth);
                    break;
                }
            }

            if (!remain) {
                toremove.add(old_auth);
                log.info("===用户【{}】 移除了 权限【{}】", dbUser.getUsername(), old_auth.getAuthority());
            }
        }
        for (Authorities old_auth : toremove) {
            //必须去掉对auth的引用后，才能删除auth!!!
            dbUser.removeAuth(old_auth);
            authoritiesRepository.delete(old_auth);
        }


//
        for (int i = 0; i < authArrays.length; i++) {
            String s_newauth = authArrays[i];

            boolean inold = false;
            for (Authorities old_auth : old_auths) {
                if (old_auth.getAuthority().toUpperCase().equals(s_newauth)) {
//                    new_auths.add(old_auth);
//                    old_auths.remove(old_auth);
                    inold = true;
//                    log.info("===用户【{}】 保持 权限【{}】 不变", dbUser.getUsername(), s_newauth);
                    break;
                }
            }

            if (inold == true) {
                inold = false;
            } else {
                Authorities new_authorities = new Authorities();
                new_authorities.setUsername(dbUser);
//                new_authorities.setAuthority(s_newauth);
//                Role role = roleRepository.getOne(s_newauth);
                Role role = roleService.findRoleById(s_newauth);
                new_authorities.setRolename(role);

                //authoritiesRepository.save(new_authorities);
                dbUser.addAuth(new_authorities);
//                new_auths.add(new_authorities);
                log.info("===用户【{}】 增加了 权限【{}】", new_authorities.getUsername().getUsername(), new_authorities.getAuthority());
            }
        }
//        for (Authorities old_auth : old_auths) {
////            authoritiesRepository.delete(old_auth);
//            dbUser.removeAuth(old_auth);
//            log.info("===用户【{}】 移除了 权限【{}】", dbUser.getUsername(), old_auth.getAuthority());
//        }


        if (StringUtils.isNotBlank(newauth)) {
            if (!newauth.toUpperCase().startsWith("ROLE_")) {
                newauth = "ROLE_" + newauth.toUpperCase();
            }
            boolean parseflag = true;
            for (Authorities auth : old_auths) {
                // if the role in the user's old auth,then keep it.
                if (auth.getAuthority().equals(newauth)) {
                    parseflag = false;
                }
            }
//            for (Authorities auth : new_auths) {
//                // if the role in the user's new selected auth,then keep it.
//                if (auth.getAuthority().equals(newauth)) {
//                    parseflag = false;
//                }
//            }

            if (parseflag) {
                //Confirm newauth is a new auth that not in system before.
                Authorities new_authority = new Authorities();
                new_authority.setUsername(dbUser);
                Role role = makeANewRole(newauth);
//                new_authority.setAuthority(newauth);
                new_authority.setRolename(role);
                dbUser.addAuth(new_authority);
                usersRepository.save(dbUser);
//                authoritiesRepository.save(new_authority);
                log.info("===用户【{}】 增加了 权限【{}】", new_authority.getUsername().getUsername(), new_authority.getAuthority());
            }
        }


//        dbUser.setAuths(new_auths);
        usersRepository.saveAndFlush(dbUser);


        map.put("success", "权限处理成功");
        return map;
    }

    @Override
    public List<UserDetailInfo> checkAllUserDetailInfoAvatar() {
        List<UserDetailInfo> userDetailInfos = userDetailInfoRepository.findAll();
        userDetailInfos.forEach(u -> {
            if (StringUtils.isBlank(u.getEmail())) {
                u.setEmail(u.getDbUser().getUsername() + "@labthink.com");
            }

            if (StringUtils.isBlank(u.getFirstName())) {
                u.setFirstName(u.getDbUser().getUsername());
            }
            if (StringUtils.isBlank(u.getLastName())) {
                u.setLastName(u.getDbUser().getUsername());
            }
            if (StringUtils.isBlank(u.getAvatar())) {

//                String storePath = this.storePath;
//                String accessUrlPrefix = this.accessUrlPath;
//                String toMd5EncodeStr = u.getEmail();
//                //生成
//
//                String filename = DrawUtils.generateAvatar(storePath, toMd5EncodeStr);
//                //写入
//                u.setAvatar(accessUrlPrefix+filename);
                u.setAvatar(createAvatarFile(u.getEmail()));
            }
            userDetailInfoRepository.save(u);
        });


        System.out.println();
        return null;
    }


    /**
     * Make a new role role.
     *
     * @param newauth the newauth
     * @return the role
     */
    public Role makeANewRole(String newauth) {
        Role role = new Role();
        role.setRolename(newauth);
        role.setRoleDescribe(newauth);
        role.setDisabled(false);
        role.setComment(newauth);
        roleService.saveRole(role);
//        roleRepository.save(role);
        return role;
    }

    /**
     * Create avatar file string.
     *
     * @param encodestr the encodestr
     * @return the url
     */
    public String createAvatarFile(String encodestr) {
        String storePath = this.storePath;
        String accessUrlPrefix = this.accessUrlPath;
        String toMd5EncodeStr = encodestr;
        //生成

        String filename = DrawUtils.generateAvatar(storePath, toMd5EncodeStr);
        //写入
//        u.setAvatar(accessUrlPrefix+filename);
        return accessUrlPrefix + filename;
    }


}