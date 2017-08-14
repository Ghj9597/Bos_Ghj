package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 犹良 on 2017/8/13 0013.
 */
public interface MenuDao  extends JpaRepository<Menu,Integer>{
    @Query(value = "from Menu m inner join fetch m.roles r inner join fetch r.users u where u.id=?")
    List<Menu> findByUser(int id);
}
