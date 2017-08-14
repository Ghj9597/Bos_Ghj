package cn.itcast.bos.web.action.system;

import cn.itcast.bos.domain.base.system.Role;
import cn.itcast.bos.service.RoleService;
import cn.itcast.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
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
@Namespace("/role")
public class RoleAction extends BaseAction<Role> {
    @Autowired
    private RoleService roleService;
    @Action(value = "findAll",results = {
            @Result(name = "success",type = "json")})
    public String findAll(){
       List<Role> roles= roleService.findAll();
        ActionContext.getContext().getValueStack().push(roles);
        return SUCCESS;
    }


    private String[] permissionIds;
    private String menuString;

    public void setPermissionIds(String[] permissionIds) {
        this.permissionIds = permissionIds;
    }


    public void setMenuString(String menuString) {
        this.menuString = menuString;
    }

    @Action(value = "save",results = {
            @Result(name = "success",location = "/pages/system/role.html",type = "redirect")})
    public String save(){
        roleService.save(t,permissionIds,menuString);
        return SUCCESS;
    }
}
