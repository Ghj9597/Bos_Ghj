package cn.itcast.bos.web.action.take_delivery.action;

import cn.itcast.bos.domain.base.take_delivery.WayBill;
import cn.itcast.bos.service.WayBillService;
import cn.itcast.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 犹良 on 2017/8/8 0008.
 */
@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/wayBill")
public class WayBillAction extends BaseAction<WayBill> {
    private int page;
    private int rows;

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setPage(int page) {

        this.page = page;
    }

    //注入service;
    @Autowired
    private WayBillService wayBillService;

    @Action(value = "save", results = {@Result(name = "success", type = "json")})
    public String save() {
        //创建日志记录;
        Logger logger = Logger.getLogger(WayBillAction.class);
        //在方法中调用业务层方法将数据保存;
        Map<String, Object> result = new HashMap<String, Object>();
        try {

            wayBillService.save(t);
            //如果执行没有异常保存SUCCESS为成功;
            result.put("success", true);
            logger.info("保存成功");
            //并且添加一句异常;
            result.put("msg", "保存成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", e.getMessage());
            logger.info("发生了不可预知的错误!!!" + e.getMessage());
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @Action(value = "pageQuery", results = {@Result(name = "success", type = "json")})
    public String pageQuery() {
        //分页查询就要设置PageAble对象;
        Pageable pageable = new PageRequest(page - 1, rows, new Sort(Sort.Direction.DESC, "id"));
        //调用方法查询;
        Page<WayBill> page = wayBillService.findAll(pageable,t);
        //调用方法封装并且推到栈顶;
        paging(page);
        return SUCCESS;
    }
    @Action(value = "findByWayBillNum",results = {@Result(name = "success", type = "json")})
    public String findByWayBillNum(){
        WayBill wayBill=wayBillService.findByWayBillNum(t.getWayBillNum());
        Map<String,Object> result=new HashMap<String,Object>();
        if (wayBill==null){
            result.put("success",false);
        }else{
            result.put("success",true);
            //将值存入;
            result.put("wayBillData",wayBill);
        }
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }
    @Action(value = "saveWayBill", results = {@Result(name = "success", type = "json")})
    public String saveWayBill(){

        //创建日志记录;
        Logger logger = Logger.getLogger(WayBillAction.class);
        //在方法中调用业务层方法将数据保存;
        Map<String, Object> result = new HashMap<String, Object>();
        //由于表单可能存在运单号重复,那就要先查询运单信息
        try {
            //判断如果没有输入地址id则从数据中移除ORDER对象;
            if (t.getOrder() != null
                    && (t.getOrder().getId() == null || t.getOrder()
                    .getId() == 0)) {
                t.setOrder(null);
            }
            //得到wayBill对象;由于它是持久态对象所以不可以将本地的wayBill对象保存进来save;
            WayBill wayBill = wayBillService.findByWayBillNum(t.getWayBillNum());
            //取出它的ID;
            if(wayBill!=null){
                Integer id = wayBill.getId();
                //拷贝本地的瞬时态对象到我的持久态对象中;
                BeanUtils.copyProperties(wayBill,t);
                wayBill.setId(id);
            }else{
                wayBillService.save(t);
            }
            //如果执行没有异常保存SUCCESS为成功;
            result.put("success", true);
            logger.info("保存成功");
            //并且添加一句异常;
            result.put("msg", "保存成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", e.getMessage());
            logger.info("发生了不可预知的错误!!!" + e.getMessage());
            e.printStackTrace();
        }
        return SUCCESS;
    }
}
