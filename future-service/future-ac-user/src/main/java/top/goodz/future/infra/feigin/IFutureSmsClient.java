package top.goodz.future.infra.feigin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.goodz.future.infra.feigin.model.FutureEmailRequest;
import top.goodz.future.infra.feigin.model.FutureEmailResponse;
import top.goodz.future.response.CommonResponse;

import java.util.List;

/**
 *  * @Description:  用户中台feign
 * *  * @throws 
 * *  * @author zhangyajun
 *  * @createTime： 20200505
 *  * @version： 1.0
 *  
 */

@FeignClient(value = "future-sms")
public interface IFutureSmsClient {

    @RequestMapping(value = "/api/email/batchSend", method = RequestMethod.POST)
    CommonResponse<List<FutureEmailResponse>> batchSend(@RequestBody FutureEmailRequest  emailRequest);
}
