package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.system.Role;
import cn.itcast.bos.domain.base.system.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 犹良 on 2017/8/13 0013.
 */
public interface RoleDao extends JpaRepository<Role,Integer> {
    List<Role> findByUsers(User user);
}
