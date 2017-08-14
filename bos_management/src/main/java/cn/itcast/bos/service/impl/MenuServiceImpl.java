package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.MenuDao;
import cn.itcast.bos.domain.base.system.Menu;
import cn.itcast.bos.domain.base.system.User;
import cn.itcast.bos.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 犹良 on 2017/8/13 0013.
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> findAll() {
        List<Menu> menus = menuDao.findAll();
        return menus;
    }

    @Override
    public void save(Menu t) {
        //先判断父菜单项目是否为null;
        if(t.getParentMenu()!=null&&t.getParentMenu().getId()==0){
          t.setParentMenu(null);
        }
        //执行保存;
        menuDao.save(t);
    }

    @Override
    public List<Menu> find(User user) {
        //查询该用户所有的菜单;
        List<Menu> menus=menuDao.findByUser(user.getId());
        return menus;
    }
}
