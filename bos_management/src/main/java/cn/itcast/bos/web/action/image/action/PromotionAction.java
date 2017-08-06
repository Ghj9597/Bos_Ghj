package cn.itcast.bos.web.action.image.action;

/**
 * Created by 犹良 on 2017/8/3 0003.
 */

import cn.itcast.bos.domain.base.Promotion;
import cn.itcast.bos.service.PromotionService;
import cn.itcast.bos.web.action.base.BaseAction;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/promotion")
public class PromotionAction extends BaseAction<Promotion>{
    private String titleImgFileFileName;
    @Autowired
    private PromotionService promotionService;
    private int page;
    private int rows;

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setPage(int page) {

        this.page = page;
    }

    public void setTitleImgFileFileName(String titleImgFileFileName) {
        this.titleImgFileFileName = titleImgFileFileName;
    }

    public void setTitleImgFile(File titleImgFile) {
        this.titleImgFile = titleImgFile;
    }

    private File titleImgFile;
    @Action(value = "save", results = {
            @Result(name = "success", type = "redirect",location = "/pages/take_delivery/promotion.html"),
            @Result(name = "error",type = "redirect",location = "/pages/take_delivery/promotion_add.html")})
    public String save(){
        //获取绝对路径;
        String path = ServletActionContext.getServletContext().getRealPath("/upload");
        //获取相对路径;
        String url = ServletActionContext.getRequest().getContextPath() + "/upload";
        //随机生成文件名;
        UUID uuid = UUID.randomUUID();
        //获取文件后缀名称;
        String substring = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
        String fileName = uuid + substring;
        File file = new File(path,fileName);
        try {
            FileUtil.copyFile(titleImgFile,file);
            File file1 = new File(url,fileName);
            t.setTitleImg(file1.toString());
            promotionService.save(t);
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR;
        }
        return SUCCESS;
    }

    @Action(value = "pageQuery",results = {@Result(name = "success",type = "json")})
    public String pageQuery(){
        Pageable pageable=new PageRequest(page-1,rows);
        Page<Promotion> promotions=promotionService.findAll(pageable);
        this.paging(promotions);
        return SUCCESS;
    }

}
