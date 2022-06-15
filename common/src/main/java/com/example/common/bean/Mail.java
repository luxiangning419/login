package com.example.common.bean;/*
 *
 * @Author: luxiangning
 * @date:  2022/6/14 下午1:23
 * @Description TODO
 */

public class Mail {
    // 用于数据库主键
    private long id;
    private String email;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
