/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.SprKi.dao.test;

import net.liuxuan.SprKi.entity.User;

/**
 * Created by Moses on 2016/2/5.
 */
public interface UserDao {
    User get(Integer id);
    void save(User user);
    void delete(User obj);

}
