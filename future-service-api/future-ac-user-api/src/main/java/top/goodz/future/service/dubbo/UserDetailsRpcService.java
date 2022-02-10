package top.goodz.future.service.dubbo;

import top.goodz.future.domian.model.user.SysUserEntity;
import top.goodz.future.response.CommonResponse;

public interface UserDetailsRpcService {

    public CommonResponse<SysUserEntity> getUserInfoByName(String name);
}
