package top.goodz.future.infra.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.goodz.future.domian.model.user.UserEntity;
import top.goodz.future.domian.repository.RegisterUserRepository;
import top.goodz.future.infra.dao.UserDao;

import javax.annotation.Resource;

/**
 *  @Description TODO
 *  @Author Yajun.Zhang
 *  @Date 2021/7/4 21:11
 */


@Repository
public class RegisterUserRepositoryImpl   implements RegisterUserRepository {

    @Autowired
    private UserDao userDao;

    @Override
    public void insert(UserEntity userEntity) {
        userDao.insert(userEntity);
    }
}
