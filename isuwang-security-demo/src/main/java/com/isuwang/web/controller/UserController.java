package com.isuwang.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.isuwang.dto.User;
import com.isuwang.exection.UserNotExitExection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/user/{id:\\d+}", method = RequestMethod.GET )
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable String id) {
        throw  new UserNotExitExection(id);
//        User user = new User();
//        user.setUsername("tom");
//        return user;
    }
}
