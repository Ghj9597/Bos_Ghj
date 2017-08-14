package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.system.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 犹良 on 2017/8/13 0013.
 */
public interface UserDao extends JpaRepository<User,Integer>{
    User findByUsername(String username);
}
