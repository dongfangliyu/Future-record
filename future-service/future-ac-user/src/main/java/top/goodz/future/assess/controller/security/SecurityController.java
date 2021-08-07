package top.goodz.future.assess.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.goodz.future.application.process.SecurityProcessService;
import top.goodz.future.assess.controller.model.request.security.SendEmailCode;
import top.goodz.future.domian.model.secuiity.SendEmailCodeVO;
import top.goodz.future.response.CommonResponse;

/**
 * @ClassName: SecurityController
 * @Desc: todo
 * @Author: YaJun.Zhang
 * Date: 2021/8/7 22:38
 **/
@RestController
@RequestMapping(value = "/api/send")
public class SecurityController {

    @Autowired
    private SecurityProcessService securityProcessService;
    @PostMapping("/emailCode")
    public CommonResponse sendEmailCode(@RequestBody SendEmailCode sendEmailCode) {

        securityProcessService.sendEmailCode(convert2SendEmailCode(sendEmailCode));

        return CommonResponse.isSuccess();
    }

    private SendEmailCodeVO convert2SendEmailCode(SendEmailCode sendEmailCode) {
        SendEmailCodeVO emailCodeVO = new SendEmailCodeVO();
        emailCodeVO.setSecurityNo(sendEmailCode.getSecurityNo());
        return emailCodeVO;
    }
}
