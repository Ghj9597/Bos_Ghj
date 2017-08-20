package cn.itcast.bos.web.action.transit;

import cn.itcast.bos.domain.base.transit.TransitInfo;
import cn.itcast.bos.service.TransitInfoService;
import cn.itcast.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by 犹良 on 2017/8/14 0014.
 */
@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/transitInfo")
public class TransitInfoAction extends BaseAction<TransitInfo>{
    private int page;
    private int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    //注入Service层;
    @Autowired
    private TransitInfoService transitInfoService;

    //创建模型驱动来接受页面传过来的字符串格式的订单id;
    private String wayBillsId;

    public void setWayBillsId(String wayBillsId) {
        this.wayBillsId = wayBillsId;
    }

    @Action(value = "create", results = { @Result(name = "success", type = "json") })
    public String create(){
        //正常情况下保存成功添加成功消息,如果发生异常的话添加异常进入;
        Map<String,Object>map=new HashMap<>();
        try {
            transitInfoService.createTransit(wayBillsId);
            map.put("success",true);
            map.put("msg","保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","保存失败>>错位信息>>"+e.getMessage());
        }
        ActionContext.getContext().getValueStack().push(map);
        return SUCCESS;
    }

    @Action(value = "findAll", results = { @Result(name = "success", type = "json") })
    public String findAll(){
        Pageable pageable=new PageRequest(page-1,rows);
        Page<TransitInfo> page=transitInfoService.findAll(pageable);
        paging(page);
        return SUCCESS;
    }
}
