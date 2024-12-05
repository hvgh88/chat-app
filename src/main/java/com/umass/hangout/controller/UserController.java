package com.umass.hangout.controller;

import com.umass.hangout.entity.User;
import com.umass.hangout.entity.Group;
import com.umass.hangout.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/groups/{userId}")
    public Set<Group> getUserGroups(@PathVariable Long userId) {
        return userService.getUserGroups(userId);
    }
}