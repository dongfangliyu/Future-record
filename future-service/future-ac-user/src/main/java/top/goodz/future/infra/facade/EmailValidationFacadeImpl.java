package top.goodz.future.infra.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.goodz.future.domian.facade.EmailValidationFacade;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.infra.feigin.IFutureSmsClient;
import top.goodz.future.infra.feigin.model.FutureEmailRequest;
import top.goodz.future.response.CommonResponse;

/**
 * @Description EmailValidationFacadeImpl
 * @Author Yajun.Zhang
 * @Date 2021/7/11 16:13
 */

@Service
public class EmailValidationFacadeImpl  implements EmailValidationFacade {

    @Autowired
    private IFutureSmsClient  futureSmsClient;

    @Override
    public void send(FutureEmailRequest request) {

        CommonResponse response = futureSmsClient.batchSend(request);

        if (!response.getCode().equals("0")){
            ErrorCodeEnum.ERROR.throwEcxeption();
        }

        System.out.printf(""+ response.getData());

    }

}
