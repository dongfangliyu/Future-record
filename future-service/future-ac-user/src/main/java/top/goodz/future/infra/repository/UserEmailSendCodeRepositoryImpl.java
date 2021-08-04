package top.goodz.future.infra.repository;

import org.springframework.stereotype.Repository;
import top.goodz.future.domian.model.secuiity.UserEmailCodeVO;
import top.goodz.future.domian.repository.UserEmailSendCodeRepository;
import top.goodz.future.infra.dao.UserEmailCodeMapper;

import javax.annotation.Resource;

/**
 * @ClassName: UserEmailSendCodeRepositoryImpl
 * @Desc: todo
 * @Author: YaJun.Zhang
 * Date: 2021/8/1 15:08
 **/

@Repository
public class UserEmailSendCodeRepositoryImpl implements UserEmailSendCodeRepository {

    @Resource
    private UserEmailCodeMapper userEmailCodeMapper;

    @Override
    public void save(UserEmailCodeVO codeVO) {
        userEmailCodeMapper.insertSelective(codeVO);
    }

    @Override
    public UserEmailCodeVO load(String emailAuthNo) {
        return userEmailCodeMapper.selectByEmailAuthNo(emailAuthNo);
    }
}
