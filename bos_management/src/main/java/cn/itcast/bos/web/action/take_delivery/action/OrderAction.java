package cn.itcast.bos.web.action.take_delivery.action;

import cn.itcast.bos.domain.base.take_delivery.Order;
import cn.itcast.bos.service.OrderService;
import cn.itcast.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 犹良 on 2017/8/8 0008.
 */
@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/order")
public class OrderAction extends BaseAction<Order>{
    @Autowired
    private OrderService orderService;

    @Action(value = "findByOrderNum", results = {@Result(name = "success", type = "json")})
    public String findByOrderNum(){
        Order order=orderService.findByOrderNum(t.getOrderNum());
        Map<String,Object> result=new HashMap<String,Object>();
        if (order==null){
            result.put("success",false);
        }else{
            result.put("success",true);
            //将值存入;
            result.put("orderData",order);
        }
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }
}
