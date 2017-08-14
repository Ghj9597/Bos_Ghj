package cn.itcast.bos.system;

import cn.itcast.bos.domain.base.system.Permission;
import cn.itcast.bos.domain.base.system.Role;
import cn.itcast.bos.domain.base.system.User;
import cn.itcast.bos.service.PermissionService;
import cn.itcast.bos.service.RoleService;
import cn.itcast.bos.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 犹良 on 2017/8/13 0013.
 */
@Service("BosRealm")
public class BosRealm extends AuthorizingRealm {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    //这个是权限校验方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前用户;
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        //查询数据库查看权限与角色;
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roles=roleService.findByUsers(user);
        //遍历角色添加进入权限对象;
        for (Role role:roles) {
            simpleAuthorizationInfo.addRole(role.getKeyword());
        }
        List<Permission> Permissions=permissionService.findByUser(user);
        for (Permission permission:Permissions) {
            System.out.println("这个用户的权限有>>>>>>>>>>>>>>>>>>>"+permission.getKeyword());
            simpleAuthorizationInfo.addStringPermission(permission.getKeyword());
        }
        //最后返回AuthorizationInfo的子类对象;
        return simpleAuthorizationInfo;
    }
//这个是登录验证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        String username = token.getUsername();
        User user = userService.findByUsername(username);
        if (user==null){
            return null;
        }else{
            return new SimpleAuthenticationInfo(user,token.getPassword(),getName());
        }

    }
}
