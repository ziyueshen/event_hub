package org.szy.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import org.springframework.stereotype.Service;
import org.szy.mapper.UserMapper;
import org.szy.entity.User;
import org.szy.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
