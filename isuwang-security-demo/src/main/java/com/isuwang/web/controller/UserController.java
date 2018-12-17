package com.isuwang.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.isuwang.dto.User;
import com.isuwang.exection.UserNotExitExection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @RequestMapping(value = "/user", method = RequestMethod.GET )
    @JsonView(User.UserSimpleView.class)
    public List<User> query() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET )
    public Object me() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping(value = "/user/{id:\\d+}", method = RequestMethod.GET )
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable String id) {
//        throw  new UserNotExitExection(id);
        System.out.println("调用服务");
        User user = new User();
        user.setUsername("tom");
        return user;
    }

    @PostMapping("/user/regist")
    public void regist(User user, HttpServletRequest request){
        //TODO 注册用户
        //不管注册用户还是绑定用户，都会拿到用户的唯一标识
        String  username= user.getUsername();

//        providerSignInUtils.doPostSignUp(String.valueOf(username),new ServletWebRequest(request));

    }
}
