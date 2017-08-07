package cn.itclass.web.action;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.take_delivery.Order;
import cn.itcast.crm.domain.Constans;
import cn.itcast.crm.domain.Customer;
import cn.itclass.web.action.cn.itclass.web.action.base.BaseAction;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;

/**
 * Created by 犹良 on 2017/8/7 0007.
 */
@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/order")
public class OrderAction extends BaseAction<Order> {
    //寄件人信息
    private String sendAreaInfo;
    //收件人信息
    private String recAreaInfo;

    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    public void setSendAreaInfo(String sendAreaInfo) {

        this.sendAreaInfo = sendAreaInfo;
    }

    @Action(value = "add",results = {@Result(name = "success",location = "/index.html", type = "redirect")})
    public String add(){
        Area sendArea=new Area();
        System.out.println(sendAreaInfo);
        System.out.println(recAreaInfo);
        String[] send = sendAreaInfo.split("/");
        sendArea.setProvince(send[0]);
        sendArea.setCity(send[1]);
        sendArea.setDistrict(send[2]);
        String[] rec =recAreaInfo.split("/");
        Area recArea=new Area();
        recArea.setProvince(rec[0]);
        recArea.setCity(rec[1]);
        recArea.setDistrict(rec[2]);
        //将封装好的省市区存入Order对象;
        t.setSendArea(sendArea);
        t.setRecArea(recArea);
        //取出Session中的用户对象并且关联进入Order之中;
        Customer customer=(Customer)ServletActionContext.getRequest().getSession().getAttribute("Customer");
        t.setCustomer_id(customer.getId());
        //调用BOS_management Service层开始执行添加操作
        WebClient.create(Constans.BOS_MANAGEMENT_URL+"/services/order/add").type(MediaType.APPLICATION_JSON).post(t);
        return SUCCESS;
    }
}
