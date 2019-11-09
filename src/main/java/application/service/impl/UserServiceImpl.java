package application.service.impl;

import org.springframework.stereotype.Service;

import application.entity.User;
import application.entity.UserExample;
import application.mapper.UserMapper;
import application.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User, UserExample> implements UserService {

}
