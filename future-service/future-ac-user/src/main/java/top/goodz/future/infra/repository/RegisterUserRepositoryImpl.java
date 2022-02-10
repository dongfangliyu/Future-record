package top.goodz.future.infra.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.goodz.future.domian.model.user.SysUserEntity;
import top.goodz.future.domian.repository.RegisterUserRepository;
import top.goodz.future.infra.dao.UserDao;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2021/7/4 21:11
 */


@Repository
public class RegisterUserRepositoryImpl implements RegisterUserRepository {

    @Autowired
    private UserDao userDao;

    @Override
    public void insert(SysUserEntity userEntity) {
        userDao.insert(userEntity);
    }

    @Override
    public void update(SysUserEntity userEntity) {
        userDao.update(userEntity);
    }

    @Override
    public SysUserEntity loadByName(SysUserEntity userEntity) {
       return userDao.loadByName(userEntity);
    }
}
