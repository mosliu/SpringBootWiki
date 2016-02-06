/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.SprKi.dao.test;

import net.liuxuan.SprKi.dao.base.MyBatisBaseDao;
import net.liuxuan.SprKi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Created by Moses on 2016/2/5.
 */
@Component
public class UserDaoImpl<T extends User> extends MyBatisBaseDao<User> implements UserDao {
    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public User get(Integer id) {
        System.out.println("-----------getUserId:" + id + "[dao start]");
        User user = null;
        try{
            user = super.get(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void save(User user) {
        super.save(user);
    }



    @Override
    public void delete(User obj) {

    }

    public Long getCount(){
        return super.getCount();
    }

    public List<Map<String, Object>> getMapList(){
        return super.getMapList(new User());
    }
}
