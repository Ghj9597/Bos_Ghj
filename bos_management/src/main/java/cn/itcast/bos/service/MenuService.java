package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.system.Menu;
import cn.itcast.bos.domain.base.system.User;

import java.util.List;

/**
 * Created by 犹良 on 2017/8/13 0013.
 */
public interface MenuService {
    List<Menu> findAll();

    void save(Menu t);

    List<Menu> find(User user);
}
