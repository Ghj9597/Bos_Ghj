package cn.itcast.bos.service.impl;


import cn.itcast.bos.dao.PermissionDao;
import cn.itcast.bos.domain.base.system.Permission;
import cn.itcast.bos.domain.base.system.User;
import cn.itcast.bos.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 犹良 on 2017/8/13 0013.
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Override
    public List<Permission> findByUser(User user) {
        List<Permission> list=permissionDao.findByUser(user.getId());
        return list;
    }

    @Override
    public List<Permission> findAll() {
        List<Permission> permissions = permissionDao.findAll();
        return permissions;
    }

    @Override
    public void save(Permission t) {
        permissionDao.save(t);
    }
}
