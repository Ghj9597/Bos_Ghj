package cn.itcast.bos.web.action.system;

import cn.itcast.bos.domain.base.system.Permission;
import cn.itcast.bos.service.PermissionService;
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
@Namespace("/permission")
public class PermissionAction extends BaseAction<Permission>{
    //注入permission的Service层进行查询;
    @Autowired
    private PermissionService permissionService;
    @Action(value = "findAll",results = {@Result(name = "success",type = "json")})
    public String findAll(){
        List<Permission> permissions=permissionService.findAll();
        ActionContext.getContext().getValueStack().push(permissions);
        return SUCCESS;
    }

    @Action(value = "save",results = {
            @Result(name = "success",location = "/pages/system/permission.html",type = "redirect")})
    public String save(){
        permissionService.save(t);
        return SUCCESS;
    }
}
