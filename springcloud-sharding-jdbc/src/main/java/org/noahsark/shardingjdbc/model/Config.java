package org.noahsark.shardingjdbc.model;

import java.util.Date;

/**
 * 配置信息
 *
 * @author zhangxt
 * @date 2022/05/10 20:06
 **/
public class Config {

    private Long id;

    private String code;

    private String name;

    private Date createDate;

    public Config() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
