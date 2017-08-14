package cn.itcast.bos.service.impl;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.CourierDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.CourierService;

import java.util.List;

@Service("courierService")
@Transactional
public class CourierServiceImpl implements CourierService{
	@Autowired
	private CourierDao courierDao;
	@RequiresPermissions("courier:add")
	@Override
	public void save(Courier courier) {
		courierDao.save(courier);
	}
	@Override
	@RequiresPermissions("courier:list")
	public Page<Courier> findAll(Specification<Courier> specification,PageRequest pageRequest) {
		Page<Courier> page = courierDao.findAll(specification,pageRequest);
		return page;
	}
	@Override
	public void delBatch(String[] split) {
		for (String idStr : split) {
			Integer id = Integer.parseInt(idStr);
			courierDao.delBatch(id);
		}
	}

	@Override
	public List<Courier> findNoFixed(Specification specification) {
		List<Courier> couriers=courierDao.findAll(specification);
		return couriers;
	}


}
