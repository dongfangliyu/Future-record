package top.goodz.future.infra.repository;

import org.springframework.stereotype.Repository;
import top.goodz.future.domian.model.secuiity.UserSecurity;
import top.goodz.future.domian.repository.SecurityVerificationRepository;
import top.goodz.future.infra.dao.UserSecurityMapper;

import javax.annotation.Resource;

/**
 * @ClassName: SecurityVerificationRepositoryImpl
 * @Desc: todo
 * @Author: YaJun.Zhang
 * Date: 2021/7/31 17:49
 **/
@Repository
public class SecurityVerificationRepositoryImpl  implements SecurityVerificationRepository {

    @Resource
    private UserSecurityMapper userSecurityMapper;

    @Override
    public void save(UserSecurity securityEntity) {
        userSecurityMapper.insertSelective(securityEntity);
    }

    @Override
    public UserSecurity load(String securityNo) {
        return userSecurityMapper.selectBySecurityNo(securityNo);
    }

    @Override
    public void update(UserSecurity entity) {
        userSecurityMapper.updateByPrimaryKeySelective(entity);
    }
}
