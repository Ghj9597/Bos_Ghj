package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.system.Role;
import cn.itcast.bos.domain.base.system.User;

import java.util.List;

/**
 * Created by 犹良 on 2017/8/13 0013.
 */
public interface RoleService {
    List<Role> findByUsers(User user);

    List<Role> findAll();

    void save(Role t, String[] permissionIds, String menuString);
}
