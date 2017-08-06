package cn.itcast.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.FixedArea;

public interface FixedAreaService {

	void addFixedArea(FixedArea fixedArea);

	Page<FixedArea> findAll(Specification<FixedArea> specification,
			Pageable pageable);

    void boundCourierTakeTime(int courierId, int takeTimeId, String id1);
}
