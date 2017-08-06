package cn.itclass.web.action;

import cn.itcast.bos.domain.base.PageBean;
import cn.itcast.bos.domain.base.Promotion;
import cn.itclass.web.action.cn.itclass.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 犹良 on 2017/8/4 0004.
 */
@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/promotion")
public class PromotionAction extends BaseAction<Promotion> {
    private  int page;
    private int pageSize;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPage(int page) {

        this.page = page;
    }


    @Action(value = "paging",results = {@Result(name = "success",type = "json")})
    public String Paging(){
        System.out.println("方法执行到啦!!!!!!!!!");
        String myurl="http://localhost:8888/bos_management/services/promotion/findAll?page="+page+"&pageSize="+pageSize;
        System.out.println(myurl);
        PageBean<Promotion> pageBean=WebClient.create(myurl).accept(MediaType.APPLICATION_JSON).get(PageBean.class);
        ActionContext.getContext().getValueStack().push(pageBean);
        System.out.println(pageBean);
        return SUCCESS;
    }
    @Action(value = "showDetail")
    public String showDetail(){
        //判断本地是否有文件,有文件的话加载本地文件,没有文件根据模板创建文件;
        String htmlRealPath = ServletActionContext.getServletContext().getRealPath("/HTMLtemplet");
        File htmlFile = new File(htmlRealPath + "/" + t.getId() + ".html");
        //没有的话根据模板生成
        if (!htmlFile.exists()){
        //第一步,配置模板生成位置;
        Configuration configuration=new Configuration(Configuration.VERSION_2_3_22);
        configuration.setServletContextForTemplateLoading(ServletActionContext.getServletContext(),"./Freemarker");
        //获取模板对象
        try {
            Template template = configuration.getTemplate("promotion_detail.ftl");
            //创建动态数据对象;
            Map<String,Object> map=new HashMap<String,Object>();
            //查询值
            String myurl="http://localhost:8888/bos_management/services/promotion//findOne/"+t.getId();
            System.out.println(myurl);
            Promotion promotion=WebClient.create(myurl).accept(MediaType.APPLICATION_JSON).get(Promotion.class);
            System.out.println(promotion);
            map.put("title",promotion.getTitle());
            map.put("area",promotion.getActiveScope());
            map.put("starttime",promotion.getStartDate().toString());
            map.put("endtime",promotion.getEndDate().toString());
            map.put("content",promotion.getDescription());
            map.put("titleImg",promotion.getTitleImg());
            //建立模板并保存到本地;
            template.process(map,new FileWriter(htmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }
    //生成后后或者本来就有就直接调用输出就好;
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        try {
            FileUtils.copyFile(htmlFile, ServletActionContext.getResponse().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }
}
