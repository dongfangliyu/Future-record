package top.goodz.future.service.repository;

import top.goodz.future.service.model.request.SlideAuthEntity;

import java.io.InputStream;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */
public interface SlideCaptcheRepository {
    void save(SlideAuthEntity entity);
}
