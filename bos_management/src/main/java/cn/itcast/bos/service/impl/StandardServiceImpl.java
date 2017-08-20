package cn.itcast.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	/*配置需要清空的缓存区并且设置为全部清空*/
	@CacheEvict(value = "standard",allEntries = true)
	@Override
	@Transactional
	public void save(Standard standard) {
		standardDao.save(standard);
	}

	@Cacheable(value = "standard",key = "#pageable.pageNumber+'_'+#pageable.pageSize")
	@Override
	public Page<Standard> findAll(Pageable pageable) {
		Page<Standard> findAll = standardDao.findAll(pageable);
		return findAll;
	}
	/*对于没有参数的查询方法直接传入缓存区名字即可*/
	@Cacheable(value = "standard")
	@Override
	public List<Standard> find() {
		List<Standard> list = standardDao.findAll();
		return list;
	}
	
}
