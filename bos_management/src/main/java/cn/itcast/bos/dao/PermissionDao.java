package cn.itcast.bos.dao;


import cn.itcast.bos.domain.base.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 犹良 on 2017/8/13 0013.
 */
public interface PermissionDao extends JpaRepository<Permission,Integer> {
    @Query(value = "from Permission p inner join fetch p.roles r inner join fetch r.users u where u.id=?")
    List<Permission> findByUser(int id);
}
