package cn.itcast.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.StandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.StandardService;
@Service("standardService")
@Transactional
public class StandardServiceImpl implements StandardService {
	
	@Autowired
	private StandardDao standardDao;

	@Override
	@Transactional
	public void save(Standard standard) {
		standardDao.save(standard);
	}

	@Override
	public Page<Standard> findAll(PageRequest pageRequest) {
		Page<Standard> findAll = standardDao.findAll(pageRequest);
		return findAll;
	}

	@Override
	public List<Standard> find() {
		List<Standard> list = standardDao.findAll();
		return list;
	}
	
}
