package cn.itcast.bos.web.action.system;

import cn.itcast.bos.domain.base.system.User;
import cn.itcast.bos.service.UserService;
import cn.itcast.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;


/**
 * Created by 犹良 on 2017/8/12 0012.
 */
@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/user")
public class UserAction extends BaseAction<User> {
    @Autowired
    private UserService userService;

    @Action(value = "login",results = {
            @Result(name = "login",location = "/login.html",type = "redirect"),
            @Result(name = "success",location = "/index.html",type = "redirect")})
    public String login(){
        //第一步创建对象;
        Subject subject = SecurityUtils.getSubject();
        //构建登录方法;
        //使用UsernamePasswordToken来构建AuthenticationToken对象;可以传入用户名和密码;
        AuthenticationToken authenticationToken=new UsernamePasswordToken(t.getUsername(),t.getPassword());
        try {
            //如果没有发生异常时,就代表登录成功并且对象存入了Subject中;
            subject.login(authenticationToken);
            return SUCCESS;
        } catch (AuthenticationException e) {
            //如果发生了异常就代表用户名不存在或者用户名和密码错误;
            e.printStackTrace();
            return LOGIN;
        }

    }
    @Action(value = "logout",results = {
            @Result(name = "success",location = "/login.html",type = "redirect")})
    public String Logout(){
        //获取Subject对象;
        Subject subject=SecurityUtils.getSubject();
        subject.logout();
        return SUCCESS;
    }

    @Action(value = "findAll",results = {
            @Result(name = "success",type = "json")})
    public String findAll(){
       List<User> users= userService.findAll();
        ActionContext.getContext().getValueStack().push(users);
        return SUCCESS;
    }
    private String[] roleIds;

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    @Action(value = "save",results = {
            @Result(name = "success",location = "/pages/system/userlist.html",type = "redirect")})
    public String save(){
        userService.save(t,roleIds);
        return SUCCESS;
    }
}
