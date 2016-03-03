/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.SprKi.entity.security;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Moses on 2016/2/3.
 */

@Entity  //实体类
@Table(name = "Sprki_User")
public class Users {

    @Id
    @NotNull
    @Column(length = 30,nullable = false)
    private String username;

    @NotNull
    @Column(length = 50,nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private boolean enabled;

    @NotNull
    @Column(length = 30)
    private String usernameAlias;

    @NotNull
    @Column(columnDefinition="TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regdate;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "username")
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

    public Set<Authorities> getAuths() {
        return auths;
    }

    public void setAuths(Set<Authorities> auths) {
        this.auths = auths;
    }
}
