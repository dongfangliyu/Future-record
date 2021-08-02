package top.goodz.future.domian.repository;

import top.goodz.future.domian.model.secuiity.UserEmailCodeVO;

public interface UserEmailSendCodeRepository {

    void save(UserEmailCodeVO codeVO);

    UserEmailCodeVO load(String emailAuthNo);
}
