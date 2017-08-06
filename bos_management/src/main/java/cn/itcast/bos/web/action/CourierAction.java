package cn.itcast.bos.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.CourierService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/courier")
public class CourierAction extends ActionSupport implements
		ModelDriven<Courier> {
	private int page;
	private int rows;
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	private Courier courier = new Courier();

	@Override
	public Courier getModel() {
		return courier;
	}

	@Autowired
	private CourierService courierService;

	@Action(value = "save", results = { @Result(name = "success", location = "/pages/base/courier.html", type = "redirect") })
	public String save() {
		courierService.save(courier);
		return SUCCESS;
	}
	@Action(value ="delBatch", results = { @Result(name = "success", location = "/pages/base/courier.html", type = "redirect")})
	public String  delBatch(){
		System.out.println(ids);
		String[] split = ids.split(",");		
		courierService.delBatch(split);
		return SUCCESS;
	}
	
	
	@Action(value = "likefind", results = { @Result(name = "success", type = "json") })
	public String likeFind() {
		PageRequest pageRequest = new PageRequest(page - 1, rows);
		Page<Courier> p = courierService.findAll(new Specification<Courier>() {

			@Override
			public Predicate toPredicate(Root<Courier> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 判断如果封装的对象中如果有值的话进行封装查询对象
				List<Predicate> array = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(courier.getCourierNum())) {
					// 通过CriteriaBuilder对象封装查询数据,root代表表中的值,判断表中的值与封装的值相同
					Predicate predicate = cb.equal(
							root.get("courierNum").as(String.class),
							courier.getCourierNum());
					array.add(predicate);
				}
				if (StringUtils.isNotBlank(courier.getCompany())) {

					Predicate predicate = cb.like(
							root.get("company").as(String.class),
							"%" + courier.getCompany() + "%");
					array.add(predicate);
				}
				if (StringUtils.isNotBlank(courier.getType())) {
					Predicate predicate = cb.equal(
							root.get("type").as(String.class),
							courier.getType());
					array.add(predicate);
				}
				// 创建附表的Root对象
				Join<Courier, Standard> standardJoin = root.join("standard",
						JoinType.INNER);
				if (courier.getStandard() != null
						&& StringUtils.isNoneBlank(courier.getStandard()
								.getName())) {
					Predicate predicate = cb.like(
							standardJoin.get("name").as(String.class), "%"
									+ courier.getStandard().getName() + "%");
					array.add(predicate);
				}

				return cb.and(array.toArray(new Predicate[0]));
			}

		}, pageRequest);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", p.getTotalElements());
		map.put("rows", p.getContent());
		ActionContext.getContext().getValueStack().push(map);
		System.out.println(map);
		return SUCCESS;
	}
	@Action(value ="findNoFixed",results = {@Result(name ="success",type ="json")})
	public String findNoFixed(){
		Specification specification=new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
				Predicate p=cb.isEmpty(root.get("fixedAreas"));
				return p;
			}
		};
		List<Courier> couriers=courierService.findNoFixed(specification);
		ActionContext.getContext().getValueStack().push(couriers);
		return SUCCESS;
	}
}
