package com.example.common.bean;

/**
 * 用户Data类
 * 映射数据库的User表
 */
public class User {
    // 用于数据库主键
    private long id;
    // 用户名，不能重复，必须是邮箱
    private String email;
    // 用户密码
    private String password;
    // 手机号，数据库设置的可以为null，客户端可以不传入
    private String phone;



    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
