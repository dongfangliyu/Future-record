package top.goodz.future.infra.repository;

import org.springframework.stereotype.Component;
import top.goodz.future.domain.repository.AutoCreateDataRepository;
import top.goodz.future.domain.service.entity.AutoTaskDataEntity;
import top.goodz.future.enums.ExceptionCode;
import top.goodz.future.exception.ServiceException;
import top.goodz.future.infra.entity.TaskDataEntity;
import top.goodz.future.infra.mapper.TaskDataEntityMapper;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;

/**
 * @Description
 * @Author Yajun.Zhang
 * @Date 2020/7/8 16:09
 */
@Component
public class AutoCreateDataRepositoryImpl implements AutoCreateDataRepository {


    @Resource
    private TaskDataEntityMapper taskDataEntityMapper;

    @Override
    public void insertData(AutoTaskDataEntity req) {

        taskDataEntityMapper.insertSelective(convert2Entity(req));
    }

    @Override
    public void idCardLenghtClean() {
        taskDataEntityMapper.updateIdCardLenght();
    }

    @Override
    public void updateAge(int minAge, int maxAge) {
        taskDataEntityMapper.updateAge(minAge,maxAge);
    }

    @Override
    public void idCardRepeatClean() {
        taskDataEntityMapper.deleteIdCardRepeat();
    }

    private TaskDataEntity convert2Entity(AutoTaskDataEntity req) {
        if (Objects.isNull(req)) {
            throw new ServiceException(ExceptionCode.PARAM_ERROR);
        }

        TaskDataEntity entity = new TaskDataEntity();
        entity.setName(req.getUserName());
        entity.setAge(req.getAge());
        entity.setUserNo("U_" + UUID.randomUUID().toString().replaceAll("-",""));
        entity.setEmail(req.getEmail());
        entity.setPhone(req.getPhone());
        entity.setIdCard(req.getIdCard());
        entity.setAreaRoad(req.getAreaRoad());
        entity.setCity(req.getCity());
        entity.setProvince(req.getProvince());
        entity.setSex(req.getSex());

        return entity;
    }
}
