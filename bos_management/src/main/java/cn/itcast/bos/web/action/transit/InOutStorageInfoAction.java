package cn.itcast.bos.web.action.transit;

import cn.itcast.bos.domain.base.transit.InOutStorageInfo;
import cn.itcast.bos.service.InOutStorageInfoService;
import cn.itcast.bos.web.action.base.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created by 犹良 on 2017/8/15 0015.
 */
@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/inOutStorageInfo")
public class InOutStorageInfoAction extends BaseAction<InOutStorageInfo> {

    @Autowired
    private InOutStorageInfoService inOutStorageInfoService;

    private String transitinfoid;

    public void setTransitinfoid(String transitinfoid) {
        this.transitinfoid = transitinfoid;
    }

    @Action(value ="save", results = { @Result(name = "success", location = "/pages/transit/transitinfo.html", type = "redirect")})
    public String save(){
        inOutStorageInfoService.save(transitinfoid,t);
        return SUCCESS;
    }
}
