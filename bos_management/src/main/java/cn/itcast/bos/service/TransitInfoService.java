package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.transit.TransitInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by 犹良 on 2017/8/14 0014.
 */
public interface TransitInfoService {
    void createTransit(String wayBillsId);

    Page<TransitInfo> findAll(Pageable pageable);
}
