package cn.itcast.bos.web.action.transit;

import cn.itcast.bos.domain.base.transit.SignInfo;
import cn.itcast.bos.service.SignInfoService;
import cn.itcast.bos.web.action.base.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created by 犹良 on 2017/8/18 0018.
 */
@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/signInfo")
public class SignInfoAction extends BaseAction<SignInfo>{
    private String transitinfoid;

    public void setTransitinfoid(String transitinfoid) {
        this.transitinfoid = transitinfoid;
    }

    @Autowired
    private SignInfoService signInfoService;

    @Action(value ="save", results = { @Result(name = "success", location = "/pages/transit/transitinfo.html", type = "redirect")})
    public String save(){
        signInfoService.save(transitinfoid,t);
        return SUCCESS;
    }
}
