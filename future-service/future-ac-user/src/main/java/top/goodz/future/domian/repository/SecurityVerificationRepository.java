package top.goodz.future.domian.repository;

import top.goodz.future.domian.model.secuiity.UserSecurity;

public interface SecurityVerificationRepository {
    void save(UserSecurity securityEntity);

    UserSecurity load(String securityNo);

    void update(UserSecurity entity);
}
