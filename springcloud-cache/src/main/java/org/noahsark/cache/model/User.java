package org.noahsark.cache.model;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author zhangxt
 * @date 2022/04/19 10:15
 **/
public class User implements Serializable {

    private Integer id;
    private String name;
    private String password;
    private String sex;
    private String nickname;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
