package cn.itcast.bos.web.action.transit;

import cn.itcast.bos.domain.base.transit.DeliveryInfo;
import cn.itcast.bos.service.DeliveryInfoService;
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
@Namespace("/deliveryInfo")
public class DeliveryInfoAction extends BaseAction<DeliveryInfo>{
    //创建方法接受参数;
    private String transitinfoid;

    public void setTransitinfoid(String transitinfoid) {
        this.transitinfoid = transitinfoid;
    }
    //注入Service层;
    @Autowired
    private DeliveryInfoService deliveryInfoService;
    @Action(value ="save", results = { @Result(name = "success", location = "/pages/transit/transitinfo.html", type = "redirect")})
    public String save(){
        deliveryInfoService.save(transitinfoid,t);
        return SUCCESS;
    }
}
