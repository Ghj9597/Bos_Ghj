package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.system.Permission;
import cn.itcast.bos.domain.base.system.User;

import java.util.List;

/**
 * Created by 犹良 on 2017/8/13 0013.
 */
public interface PermissionService {
    List<Permission> findByUser(User user);

    List<Permission> findAll();

    void save(Permission t);
}
