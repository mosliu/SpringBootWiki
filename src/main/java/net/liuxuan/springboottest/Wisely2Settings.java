/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.springboottest;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Moses on 2016/2/4.
 */
@ConfigurationProperties(prefix = "wisely2")
public class Wisely2Settings {
    private String name;
    private String gender;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
}
