package top.goodz.future.domain.service;

import top.goodz.future.domain.service.entity.AutoTaskDataEntity;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */
public interface AutoCreateDataService {


    void insertData(AutoTaskDataEntity emailResponse);

    void idCardLenghtClean();

    void ageClean(int minAge, int maxAge);

    void idCardRepeatClean();
}
