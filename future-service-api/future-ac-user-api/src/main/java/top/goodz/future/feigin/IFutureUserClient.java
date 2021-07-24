package top.goodz.future.feigin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.goodz.future.domian.model.SysLoginRequestBean;
import top.goodz.future.response.CommonResponse;

/**
 *  * @Description:  用户中台feign
 * *  * @throws 
 * *  * @author zhangyajun
 *  * @createTime： 20200505
 *  * @version： 1.0
 *  
 */

@FeignClient(value = "future-ac-user", url = "127.0.0.1:8083")
public interface IFutureUserClient {

    @RequestMapping(value = "/login/auth", method = RequestMethod.POST)
    CommonResponse login(@RequestBody SysLoginRequestBean request);

}
