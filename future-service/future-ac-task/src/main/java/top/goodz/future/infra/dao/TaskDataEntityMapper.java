package top.goodz.future.infra.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.goodz.future.infra.entity.TaskDataEntity;

@Mapper
public interface TaskDataEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaskDataEntity record);

    int insertSelective(TaskDataEntity record);

    TaskDataEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskDataEntity record);

    int updateByPrimaryKey(TaskDataEntity record);

    void updateIdCardLenght();

    void updateAge(@Param("minAge") int minAge, @Param("maxAge") int maxAge);

    void deleteIdCardRepeat();
}