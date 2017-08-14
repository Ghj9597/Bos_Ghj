package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.system.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 犹良 on 2017/8/13 0013.
 */
public interface UserService {
    User findByUsername(String username);

    List<User> findAll();

    void save(User t, String[] roleIds);
}
