package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.transit.InOutStorageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 犹良 on 2017/8/18 0018.
 */
public interface InOutStorageInfoDao extends JpaRepository<InOutStorageInfo,Integer> {
}
