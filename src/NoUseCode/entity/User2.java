package net.liuxuan.SprKi.entity;

public class User2 {
    private Integer id;

    private String username;

    public User2(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public User2() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
}