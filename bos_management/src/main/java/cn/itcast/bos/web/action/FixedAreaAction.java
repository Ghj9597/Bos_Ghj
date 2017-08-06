package cn.itcast.bos.web.action;


import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.FixedAreaService;
import cn.itcast.bos.web.action.base.BaseAction;
import cn.itcast.crm.domain.Customer;
@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/fixedArea")
public class FixedAreaAction extends BaseAction<FixedArea>{
	private FixedArea fixedArea=this.t;
	private int page;
	private int rows;
	private int courierId;

	public void setCourierId(int courierId) {
		this.courierId = courierId;
	}

	private int takeTimeId;

	public void setTakeTimeId(int takeTimeId) {
		this.takeTimeId = takeTimeId;
	}



	private String[] customerIds;
	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Autowired
	private FixedAreaService fixedAreaService;
	@Action(value="addFixedArea",results={@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
	public String addFixedArea(){
		fixedAreaService.addFixedArea(fixedArea);
		return SUCCESS;
	}
	
	@Action(value="findFixedArea",results={@Result(name = "success",type="json")})
	public String findFixedArea(){
		Pageable pageable=new PageRequest(page-1, rows);
		Specification<FixedArea> specification =new Specification<FixedArea>() {

			@Override
			public Predicate toPredicate(Root<FixedArea> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				ArrayList<Predicate> list = new ArrayList<Predicate>();
				if(StringUtils.isNoneBlank(fixedArea.getId())){
					Predicate predicate = cb.equal(root.get("id").as(int.class),fixedArea.getId());
					list.add(predicate);
				}
				if(StringUtils.isNoneBlank(fixedArea.getCompany())){
					Predicate predicate = cb.like(root.get("company").as(String.class),"%"+fixedArea.getCompany()+"%");
					list.add(predicate);
				}
				if(StringUtils.isNoneBlank(fixedArea.getFixedAreaName())){
					Predicate predicate = cb.like(root.get("fixedAreaName").as(String.class),"%"+fixedArea.getFixedAreaName()+"%");
					list.add(predicate);
				}
				
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Page<FixedArea> page=fixedAreaService.findAll(specification,pageable);
		this.paging(page);
		return SUCCESS;
	}
	@Action(value="findCustomer",results={@Result(name = "success",type="json")})
	public String findCustomer(){
		 System.out.println("运行到了");
		 Collection<? extends Customer> collection = WebClient.create("http://localhost:9999/crm_management/services/customer/noassociationcustomers").accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		 System.out.println(collection.toString());
		 ActionContext.getContext().getValueStack().push(collection);
		return SUCCESS;
	}
	@Action(value="findAssocationCustomers",results={@Result(name = "success",type="json")})
	public String findAssocationCustomers(){
		 Collection<? extends Customer> collection = WebClient.create("http://localhost:9999/crm_management/services/customer/customers/"+fixedArea.getId()).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		 System.out.println("============>>>>>>>>>"+collection.toString());
		 ActionContext.getContext().getValueStack().push(collection);
		return SUCCESS;
	}
	
	@Action(value="boundCustomer",results={@Result(name = "success",location = "/pages/base/fixed_area.html", type = "redirect")})
	public String boundCustomer(){
	System.out.println("=============>>>>>>>>>>进入方法内");
		String join = StringUtils.join(customerIds,",");		
			WebClient.create("http://localhost:9999/crm_management/services/customer/customers?customersId="+join+"&"+"fixedAreaId="+fixedArea.getId()).type(MediaType.APPLICATION_JSON).put(null);	
		return SUCCESS;
	}

	@Action(value="boundCourierTakeTime",results={@Result(name = "success",location = "/pages/base/fixed_area.html", type = "redirect")})
	public String boundCourierTakeTime(){
		fixedAreaService.boundCourierTakeTime(courierId,takeTimeId,fixedArea.getId());
		return SUCCESS;
	}
}
