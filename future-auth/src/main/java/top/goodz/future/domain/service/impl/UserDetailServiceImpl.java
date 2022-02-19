package top.goodz.future.domain.service.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.core.tool.utils.Func;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.goodz.future.domain.model.FutureUserDetails;
import top.goodz.future.domain.service.SysUserService;
import top.goodz.future.domian.model.user.SysUserEntity;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.response.CommonResponse;
import top.goodz.future.service.dubbo.SecurityVerificationRpcService;
import top.goodz.future.service.dubbo.UserDetailsRpcService;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


@Service
public class UserDetailServiceImpl implements SysUserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @DubboReference
    private UserDetailsRpcService userDetailsRpcService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        CommonResponse<SysUserEntity> response = userDetailsRpcService.getUserInfoByName(name);

        if (!CommonResponse.success(response)){
            logger.error("-----loadUserByUsername response error{}", response);
            ErrorCodeEnum.ERROR.throwEcxeption();
        }

        logger.info("----UserDetailServiceImpl loadUserByUsername  req:{} response info{}",name, response);

        SysUserEntity user = response.getData();

        if (Objects.isNull(user)){
            ErrorCodeEnum.ERROR.throwEcxeption();
        }

        return new FutureUserDetails(user.getUserNo(), user.getUserName(), user.getRealName(), user.getEmail(),
                user.getUserType(), user.getUserSource(), user.getChannelSource(), user.getReferees(), user.getReferees(), new Date(),
                user.getAuthStatus(), user.getAuthStatus(), user.getStatus(), user.getPassWord(),
                Func.join(user.getList()), true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList(
                        Func.join(user.getList())));
    }
}
