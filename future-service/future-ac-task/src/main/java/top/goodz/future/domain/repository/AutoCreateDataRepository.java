package top.goodz.future.domain.repository;

import top.goodz.future.domain.service.entity.AutoTaskDataEntity;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */
public interface AutoCreateDataRepository {
    void insertData(AutoTaskDataEntity req);

    void idCardLenghtClean();

    void updateAge(int minAge, int maxAge);

    void idCardRepeatClean();
}
