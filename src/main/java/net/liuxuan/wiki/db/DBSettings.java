/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.wiki.db;

/**
 * Created by Moses on 2016/2/3.
 */
/*通过“@ConfigurationProperties(prefix="db")”注解，
配置属性中以“db”为前缀的属性值会被自动绑定到 Java 类中同名的域上，
如 url 域的值会对应属性“db.url”的值。
只需要在应用的配置类中添加“@EnableConfigurationProperties”注解就可以启用该自动绑定功能。*/

//@ConfigurationProperties(prefix = "db")

public class DBSettings {
    private String url;
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
