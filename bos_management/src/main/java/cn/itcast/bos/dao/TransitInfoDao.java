package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.transit.TransitInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 犹良 on 2017/8/14 0014.
 */
public interface TransitInfoDao extends JpaRepository<TransitInfo,Integer> {
}
