/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.SprKi.entity.security;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import net.liuxuan.SprKi.entity.user.UserDetailInfo;
import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Moses on 2016/2/3.
 */
//@ToString
@Entity  //实体类
@Table(name = "Sprki_User")
@IgnoreSizeOf
public class DbUser {

    @Id
    @NotNull
    @Column(length = 30,nullable = false)
    private String username;

    @NotNull
    @Column(length = 50,nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private boolean enabled = true;

    @NotNull
    @Column(length = 30)
    private String usernameAlias;

    @NotNull
    @Column(columnDefinition="TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regdate;




    // 添加该项目，使用sql 更新数据库
    /*
    update sprki_user u left join sprki_labthink_user_detail_info d on u.username = d.db_user
    set u.user_detail_info = d.id
    */
//    @OneToOne(mappedBy="dbUser")
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_detail_info")
    @JsonIgnore
    protected UserDetailInfo userDetailInfo;//设备

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "username")
//@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "username")
    private Set<Authorities> auths = new HashSet<Authorities>();

    /**
     * 添加一个权限
     * @param auth
     */
    public void addAuth(Authorities auth){
        if(!this.auths.contains(auth)){
            this.auths.add(auth);
            auth.setUsername(this);
        }
    }

    /**
     * 删除一个权限
     * @param auth
     */
    public void removeAuth(Authorities auth){
        if(this.auths.contains(auth)){
            auth.setUsername(null);
            this.auths.remove(auth);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsernameAlias() {
        return usernameAlias;
    }

    public void setUsernameAlias(String usernameAlias) {
        this.usernameAlias = usernameAlias;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }
//    @JsonIgnore
    public Set<Authorities> getAuths() {
        return auths;
    }
//    @JsonIgnore
    public void setAuths(Set<Authorities> auths) {
        this.auths = auths;
    }

    public UserDetailInfo getUserDetailInfo() {
        return userDetailInfo;
    }

    public void setUserDetailInfo(UserDetailInfo userDetailInfo) {
        this.userDetailInfo = userDetailInfo;
    }

    @PrePersist
    void fillnull(){
        if(regdate==null){
            regdate = new Date();
        }
        if(usernameAlias==null){
            usernameAlias= username;
        }
        if(password == null){
            password = username;
        }

    }
}
