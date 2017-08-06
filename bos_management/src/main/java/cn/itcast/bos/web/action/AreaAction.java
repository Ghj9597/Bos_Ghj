package cn.itcast.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.AreaService;
import cn.itcast.bos.utiles.PinYin4jUtils;
import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/area")
public class AreaAction extends BaseAction<Area>{
	private File file;
    private String fileContentType;
	private String fileFilename;
	private int page;
	private int rows;
	private Area area=this.t;
	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void setFileFilename(String fileFilename) {
		this.fileFilename = fileFilename;
	}

	public void setFile(File file) {
		this.file = file;
	}
	@Autowired
	private AreaService areaService;
	
	@Action(value = "areaUpload")
	public String areaUpload(){
		try {
			List<Area>list=new ArrayList<Area>();
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheetAt = workbook.getSheetAt(0);
			for (Row row : sheetAt) {
				if (row.getRowNum() == 0) {
					// 第一行 跳过
					continue;
				}
				if(row.getCell(0)==null||StringUtils.isBlank(row.getCell(0).getStringCellValue())){
					continue;
				}
				Area area = new Area();
				area.setId(row.getCell(0).getStringCellValue());
				area.setProvince(row.getCell(1).getStringCellValue());
				area.setCity(row.getCell(2).getStringCellValue());
				area.setDistrict(row.getCell(3).getStringCellValue());
				area.setPostcode(row.getCell(4).getStringCellValue());
				String province = area.getProvince();
				String city = area.getCity();
				String district = area.getDistrict();
				//截取城市编码去除最后的市字;
				province=province.substring(0, province.length()-1);
				city = city.substring(0, city.length() - 1);
				district = district.substring(0, district.length() - 1);
				//获取首字母大写的城市简码数组;
				String[] headArray = PinYin4jUtils.getHeadByString(province + city
						+ district);
				StringBuffer buffer = new StringBuffer();
				for (String headStr : headArray) {
					buffer.append(headStr);
				}
				String shortcode = buffer.toString();
				area.setShortcode(shortcode);
				// 城市编码,以空格分割
				String citycode = PinYin4jUtils.hanziToPinyin(city,"");
				area.setCitycode(citycode);
				list.add(area);
			}
			//调用业务层保存城市数据;
			// 调用业务层
			areaService.saveBatch(list);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE;	
	}
	@Action(value = "findAll", results = {@Result(name = "success", type = "json") })
	public String findAll() {
		//进行分页;
		Pageable pageable=new PageRequest(page-1, rows);
		Specification<Area> specification=new Specification<Area>() {

			@Override
			public Predicate toPredicate(Root<Area> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate>list=new ArrayList<Predicate>();
				if(StringUtils.isNoneBlank(area.getProvince())){
					Predicate predicate = cb.like(root.get("province").as(String.class),"%"+area.getProvince()+"%");
					list.add(predicate);
				}
				if(StringUtils.isNotBlank(area.getCity())){
					Predicate predicate = cb.like(root.get("city").as(String.class),"%"+area.getCity()+"%");
					list.add(predicate);
				}
				if(StringUtils.isNotBlank(area.getDistrict())){
					Predicate predicate = cb.like(root.get("district").as(String.class),"%"+area.getDistrict()+"%");
					list.add(predicate);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Page<Area> all = areaService.findAll(pageable,specification);
		this.paging(all);
		return SUCCESS;
	}
}
