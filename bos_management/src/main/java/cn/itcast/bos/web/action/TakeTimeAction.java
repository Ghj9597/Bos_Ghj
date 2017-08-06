package cn.itcast.bos.web.action;

import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.TakeTimeService;
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
 * Created by 犹良 on 2017/7/31 0031.
 */
@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/takeTime")
public class TakeTimeAction extends BaseAction<TakeTime> {
    @Autowired
    private TakeTimeService takeTimeService;

    @Action(value = "findAll", results = {@Result(name = "success", type = "json")})
    public String findAll() {
        List<TakeTime> takeTimes = takeTimeService.findAll();
        ActionContext.getContext().getValueStack().push(takeTimes);
        return SUCCESS;
    }
}
