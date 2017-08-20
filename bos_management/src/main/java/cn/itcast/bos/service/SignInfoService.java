package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.transit.SignInfo;

/**
 * Created by 犹良 on 2017/8/18 0018.
 */
public interface SignInfoService {
    void save(String transitinfoid, SignInfo t);
}
