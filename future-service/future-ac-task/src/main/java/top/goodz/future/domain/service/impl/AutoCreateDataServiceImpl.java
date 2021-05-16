package top.goodz.future.domain.service.impl;

import org.springframework.stereotype.Service;
import top.goodz.future.domain.repository.AutoCreateDataRepository;
import top.goodz.future.domain.service.AutoCreateDataService;
import top.goodz.future.domain.service.entity.AutoTaskDataEntity;

import javax.annotation.Resource;

/**
 * @Description
 * @Author Yajun.Zhang
 * @Date 2020/7/8 14:12
 */
@Service
public class AutoCreateDataServiceImpl implements AutoCreateDataService {


    @Resource
    private AutoCreateDataRepository autoCreateDataRepository;

    @Override
    public void insertData(AutoTaskDataEntity req) {
        autoCreateDataRepository.insertData(req);
    }

    @Override
    public void idCardLenghtClean() {
        autoCreateDataRepository.idCardLenghtClean();
    }

    @Override
    public void ageClean(int minAge, int maxAge) {
        autoCreateDataRepository.updateAge(minAge, maxAge);
    }

    @Override
    public void idCardRepeatClean() {
        autoCreateDataRepository.idCardRepeatClean();
    }
}
