package top.goodz.future.domian.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.goodz.future.constants.FutureConstant;
import top.goodz.future.domian.RegisterUserService;
import top.goodz.future.domian.model.enums.UserRegisterType;
import top.goodz.future.domian.model.user.UserEntity;
import top.goodz.future.domian.repository.RegisterUserRepository;
import top.goodz.future.utils.Md5Utils;
import top.goodz.future.utils.random.RandomUtil;

/**
 *  @Description TODO
 *  @Author Yajun.Zhang
 *  @Date 2021/7/4 20:39
 */
@Service
public class RegisterUserServiceImpl  implements RegisterUserService {


    @Autowired
    private RegisterUserRepository  registerUserRepository;
    @Override
    @Transactional
    public UserEntity register(UserEntity userEntity) {

        // 二次加密
        userEntity.setPassword( Md5Utils.EncoderByMd5(userEntity.getPassword()));
        userEntity.setUserNo(RandomUtil.randomCharString(32));
        userEntity.setStatus(FutureConstant.COMMONT_STATUS_SUCCESS);
        userEntity.setType(UserRegisterType.EMAIL.getCode());

        registerUserRepository.insert(userEntity);

        return userEntity;
    }
}
