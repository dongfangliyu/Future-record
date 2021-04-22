package top.goodz.future.service;

import top.goodz.future.service.model.request.SlideAuthEntity;
import top.goodz.future.service.model.response.SlideCheckResultVO;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime：
 *  * @version：1.0
 *  
 */
public interface CaptchaService {
    SlideAuthEntity createSlideCaptchaVerification();

    SlideCheckResultVO check(String uuid, Integer valueOf, Integer valueOf1);

    SlideCheckResultVO auth(String authId);
}
