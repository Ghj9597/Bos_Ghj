package cn.itcast.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Courier;

import java.util.List;

public interface CourierService {

	void save(Courier courier);

	Page<Courier> findAll(Specification<Courier> specification,
			PageRequest pageRequest);

	void delBatch(String[] split);

	List<Courier> findNoFixed(Specification specification);
}
