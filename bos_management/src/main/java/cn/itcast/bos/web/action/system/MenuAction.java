package cn.itcast.bos.web.action.system;

import cn.itcast.bos.domain.base.system.Menu;
import cn.itcast.bos.domain.base.system.User;
import cn.itcast.bos.service.MenuService;
import cn.itcast.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import org.apache.shiro.SecurityUtils;
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
 * Created by 犹良 on 2017/8/13 0013.
 */
@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/menu")
public class MenuAction extends BaseAction<Menu> {
    @Autowired
    private MenuService menuService;
    @Action(value = "findAll",results = {@Result(name = "success",type = "json")})
    public String findAll(){
        List<Menu> menus=menuService.findAll();
        ActionContext.getContext().getValueStack().push(menus);
        return SUCCESS;
    }
    @Action(value = "save",results = {
            @Result(name = "success",location = "/pages/system/menu.html",type = "redirect")})
    public String save(){
        menuService.save(t);
        return SUCCESS;
    }

    @Action(value = "find",results = {@Result(name = "success",type = "json")})
    public String find(){
        Subject subject= SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        List<Menu> menus=menuService.find(user);
        ActionContext.getContext().getValueStack().push(menus);
        return SUCCESS;
    }
}
