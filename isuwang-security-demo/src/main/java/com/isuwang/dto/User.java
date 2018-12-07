package com.isuwang.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.codehaus.jackson.annotate.JsonValue;

public class User  {

    //使用接口声明多个视图
    public interface UserSimpleView {};
    public interface UserDetailView extends UserSimpleView {};

    private String username;

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;


}
