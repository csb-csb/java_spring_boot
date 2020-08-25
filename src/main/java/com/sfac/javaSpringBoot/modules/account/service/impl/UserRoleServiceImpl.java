package com.sfac.javaSpringBoot.modules.account.service.impl;

import com.sfac.javaSpringBoot.modules.account.dao.UserDao;
import com.sfac.javaSpringBoot.modules.account.dao.UserRoleDao;
import com.sfac.javaSpringBoot.modules.account.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
private UserRoleDao userRoleDao;
    @Autowired
    private UserDao userDao;
}
