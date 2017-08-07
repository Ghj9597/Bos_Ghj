package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.CourierDao;
import cn.itcast.bos.dao.TakeTimeDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.TakeTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.FixedAreaDao;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.FixedAreaService;
@Service("fixedAreaService")
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
	@Autowired
	private FixedAreaDao fixedAreaDao;
	@Override
	public void addFixedArea(FixedArea fixedArea) {
		fixedAreaDao.save(fixedArea);
	}
	@Override
	public Page<FixedArea> findAll(Specification<FixedArea> specification,
			Pageable pageable) {
		Page<FixedArea> page = fixedAreaDao.findAll(specification,pageable);
		return page;
	}
	@Autowired
	private CourierDao courierDao;
	@Autowired
	private TakeTimeDao takeTimeDao;
	@Override
	public void boundCourierTakeTime(int courierId, int takeTimeId, String id1) {
		Courier courier = courierDao.findOne(courierId);
		TakeTime takeTime = takeTimeDao.findOne(takeTimeId);
		FixedArea one = fixedAreaDao.findOne(id1);
		one.getCouriers().add(courier);
		// 将收派标准 关联到 快递员上
		courier.setTakeTime(takeTime);
	}


}
