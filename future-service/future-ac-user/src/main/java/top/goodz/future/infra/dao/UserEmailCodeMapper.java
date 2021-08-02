package top.goodz.future.infra.dao;

import org.apache.ibatis.annotations.Param;
import top.goodz.future.domian.model.secuiity.UserEmailCodeVO;

public interface UserEmailCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserEmailCodeVO record);

    int insertSelective(UserEmailCodeVO record);

    UserEmailCodeVO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserEmailCodeVO record);

    int updateByPrimaryKey(UserEmailCodeVO record);

    UserEmailCodeVO selectByEmailAuthNo(@Param("emailAuthNo") String emailAuthNo);
}