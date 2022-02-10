package top.goodz.future.domian.impl.dubbo;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import top.goodz.future.domian.model.user.SysUserEntity;
import top.goodz.future.response.CommonResponse;
import top.goodz.future.service.dubbo.UserDetailsRpcService;
import top.goodz.future.utils.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@DubboService
public class UserDetailsServiceImpl implements UserDetailsRpcService {


    @Override
    public CommonResponse getUserInfoByName(String name) {

        List<SysUserEntity> entities = convertSysUserEntity(name);
        log.info("------UserDetailsServiceImpl  [ac-user] req:{},resp：{}", name, entities);
        SysUserEntity sysUserEntity = entities.stream()
                .filter(s -> s.getUserName().equalsIgnoreCase(name))
                .findFirst().get();

        return CommonResponse.responseOf(sysUserEntity);
    }

    private List<SysUserEntity> convertSysUserEntity(String source) {
        List<SysUserEntity> list = Lists.newArrayList();
        SysUserEntity target = new SysUserEntity();
        target.setId(1);
        target.setUserName("test");
        target.setRealName("test");
        target.setEmail("1339811979@qq.com");
        target.setUserSource("web");
        target.setChannelSource("share");
        target.setAccountName("user");
        target.setPassWord("$2a$10$KSfjSy1/i28LTBK68OFEaeZKwBB351fMwKzk22zfp0i2JarbfkxUu");
        target.setLastLoginTime(DateUtil.getCurrentTime());
        target.setUserType(1);
        target.setStatus(1);
        target.setAuthStatus(1);
        target.setUserNo("FSSFSSS");
        target.setReferees("1");
        target.setCreateTime(new Date());
        target.setList(Arrays.asList("USER"));

        SysUserEntity targe = new SysUserEntity();
        targe.setId(1);
        targe.setUserName("adminUser");
        targe.setRealName("adminUser");
        targe.setEmail("13398119791@qq.com");
        targe.setUserSource("web");
        targe.setChannelSource("share1");
        targe.setAccountName("adminUser");
        targe.setPassWord("$2a$10$KSfjSy1/i28LTBK68OFEaeZKwBB351fMwKzk22zfp0i2JarbfkxUu");
        targe.setLastLoginTime(DateUtil.getCurrentTime());
        targe.setUserType(1);
        targe.setStatus(1);
        targe.setAuthStatus(1);
        targe.setUserNo("00002");
        targe.setReferees("1");
        targe.setCreateTime(new Date());
        targe.setList(Arrays.asList("USER", "ADMIN"));

        SysUserEntity targe1 = new SysUserEntity();
        targe1.setId(1);
        targe1.setUserName("manager");
        targe1.setRealName("manager");
        targe1.setEmail("13398119792@qq.com");
        targe1.setUserSource("web");
        targe1.setChannelSource("share2");
        targe1.setAccountName("adminUser");
        targe1.setPassWord("$2a$10$KSfjSy1/i28LTBK68OFEaeZKwBB351fMwKzk22zfp0i2JarbfkxUu");
        targe1.setLastLoginTime(DateUtil.getCurrentTime());
        targe1.setUserType(1);
        targe1.setStatus(1);
        targe1.setAuthStatus(1);
        targe1.setUserNo("00003");
        targe1.setReferees("1");
        targe1.setCreateTime(new Date());
        targe1.setList(Arrays.asList("MANAGER"));

        list.add(target);
        list.add(targe);
        list.add(targe1);

        return list;
    }

}
