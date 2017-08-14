package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.RoleDao;
import cn.itcast.bos.dao.UserDao;
import cn.itcast.bos.domain.base.system.Role;
import cn.itcast.bos.domain.base.system.User;
import cn.itcast.bos.service.RoleService;
import cn.itcast.bos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by 犹良 on 2017/8/13 0013.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Override
    public User findByUsername(String username) {
       User user= userDao.findByUsername(username);
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = userDao.findAll();
        return users;
    }

    @Override
    public void save(User t, String[] roleIds) {
        //先保存user对象使他变为持久态对象;
        userDao.save(t);
        if(roleIds!=null&&roleIds.length!=0){
            //不为空时遍历查询用户对应的角色
            Set<Role> roles = t.getRoles();
            for (String string:roleIds) {
                int i = Integer.parseInt(string);
                Role role = roleDao.findOne(i);
                roles.add(role);
            }
        }
    }
}
