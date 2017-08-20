package cn.itcast.bos.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.StandardService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

//创建Action
@ParentPackage("json-default")
@Controller
@Scope(value="prototype")
@Namespace("/standard")
public class StandardAction extends ActionSupport implements ModelDriven<Standard>{
	private Standard standard=new Standard();
	@Override
	public Standard getModel() {
		return standard;
	}
	@Autowired
	private StandardService standardService;
	
	//从页面中接受发送来的分页请求,page和rows;
	private int page;
	private int rows;
	//	实现set方法
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

	// /bos_managemetn/sava.action
	// /bos_management/   pages/base/standard.html
	// /bos_managemant/standard/pages/base/standard.html	
	@Action(value="save",results={@Result(name="success",location="/pages/base/standard.html",type = "redirect")})
	public String save(){
		//调用Service层添加数据
		System.out.println("运行到这里了");
		standardService.save(standard);
		return SUCCESS;
	}
	
	@Action(value="findAll",results={@Result(name="success",type="json")})
	public String findAll(){
		System.out.println("执行到了");
		Pageable pageable=new PageRequest(page-1,rows);
		Page<Standard> p=standardService.findAll(pageable);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("total",p.getTotalElements());
		map.put("rows",p.getContent());
		ActionContext.getContext().getValueStack().push(map);
		System.out.println(map);
		return SUCCESS;
	}
	@Action(value="find",results={@Result(name="success",type="json")})	
	public String find(){
		List<Standard>list=standardService.find();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	}
}
