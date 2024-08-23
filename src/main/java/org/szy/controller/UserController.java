package org.szy.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.szy.common.BaseContext;
import org.szy.common.R;
import org.szy.entity.User;
import org.szy.service.UserService;
import org.szy.utils.JwtUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R login(@RequestBody User user) {
        String userName = user.getName();
        String password = user.getPassword();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, userName);
        user = userService.getOne(queryWrapper);
        if (user == null) {
            return R.error("Login failed");
        }

        if (!user.getPassword().equals(password)) {
            return R.error("Login failed");
        }
        Long userId = user.getId();
        String token = JwtUtils.generateToken(userId);
        Map<String, String> mp = new HashMap<>();
        mp.put("token", token);
        return R.success(mp);
    }
    @GetMapping("/info")
    public R info() {
        log.info("Get info...");
        Long userId = BaseContext.getCurrentId();
        User user = userService.getById(userId);
        Map<String, Object> mp = new HashMap<>();
        mp.put("name", user.getName());
        mp.put("city", user.getPreferCity());
        String tagsCategory = user.getPreferCategory();
        String tagsFormat = user.getPreferFormat();
        List<String> tagCategoryList = JSON.parseArray(tagsCategory, String.class);
        List<String> tagsFormatList = JSON.parseArray(tagsFormat, String.class);
        mp.put("preferCategory", tagCategoryList);
        mp.put("preferFormat", tagsFormatList);
        return R.success(mp);
    }
}
