package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.MenuDao;
import cn.itcast.bos.dao.PermissionDao;
import cn.itcast.bos.dao.RoleDao;
import cn.itcast.bos.domain.base.system.Menu;
import cn.itcast.bos.domain.base.system.Permission;
import cn.itcast.bos.domain.base.system.Role;
import cn.itcast.bos.domain.base.system.User;
import cn.itcast.bos.service.RoleService;
import org.apache.commons.lang3.StringUtils;
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
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private MenuDao menuDao;
    @Override
    public List<Role> findByUsers(User user) {
        List<Role> roles= roleDao.findByUsers(user);
        return roles;
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = roleDao.findAll();
        return roles;
    }

    @Override
    public void save(Role t, String[] permissionIds, String menuString) {
        //先保存Role使他变为一个持久态对象;
        roleDao.save(t);
        //判断permissionIds中是否有值;
        if (permissionIds!=null&&permissionIds.length!=0){
            Set<Permission> permissions = t.getPermissions();
            for (String s:permissionIds) {
                int i = Integer.parseInt(s);
                Permission permission = permissionDao.findOne(i);
                permissions.add(permission);
            }
        }
        if(StringUtils.isNoneBlank(menuString)){
            String[] split = menuString.split(",");
            Set<Menu> menus = t.getMenus();
            for (String s:split){
                int i = Integer.parseInt(s);
                Menu one = menuDao.findOne(i);
                menus.add(one);
            }
        }
    }
}
