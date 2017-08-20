package cn.itcast.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.domain.Pageable;


public interface StandardService {

	void save(Standard standard);

	Page<Standard> findAll(Pageable pageable);

	List<Standard> find();
	
}
