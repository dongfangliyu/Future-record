package top.goodz.future.domian.impl;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.goodz.future.domian.RegisterUserService;
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
    public void register(UserEntity userEntity) {

        // 二次加密
        userEntity.setPassword( Md5Utils.EncoderByMd5(userEntity.getPassword()));
        userEntity.setUserNo(RandomUtil.randomCharString(32));
        userEntity.setStatus(0);
        userEntity.setType(1);

        registerUserRepository.insert(userEntity);
    }
}
