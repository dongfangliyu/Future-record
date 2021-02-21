package top.goodz.future.service;

import top.goodz.future.service.model.request.SlideAuthEntity;

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
}
