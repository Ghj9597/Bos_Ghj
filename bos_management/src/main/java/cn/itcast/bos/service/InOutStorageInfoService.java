package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.transit.InOutStorageInfo;

/**
 * Created by 犹良 on 2017/8/15 0015.
 */
public interface InOutStorageInfoService {
    void save(String transitinfoid, InOutStorageInfo t);
}
