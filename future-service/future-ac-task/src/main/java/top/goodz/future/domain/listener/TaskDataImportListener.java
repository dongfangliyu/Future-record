package top.goodz.future.domain.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.goodz.future.domain.listener.model.AutoTaskDataVO;
import top.goodz.future.domain.service.AutoCreateDataService;
import top.goodz.future.domain.service.entity.AutoTaskDataEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2021/5/18 22:17
 */
@Component
public class TaskDataImportListener extends AnalysisEventListener<AutoTaskDataVO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskDataImportListener.class);


    public TaskDataImportListener(AutoCreateDataService autoCreateDataService) {
        this.autoCreateDataService = autoCreateDataService;
    }

    private AutoCreateDataService autoCreateDataService;

    //临时缓存区
    private List<AutoTaskDataVO> applyInfos = new ArrayList<AutoTaskDataVO>();


    private Map<String, Object> analysisMap = new HashMap<>();


    @Override
    public void invoke(AutoTaskDataVO dataVO, AnalysisContext context) {
        int currentRowNum = context.getCurrentRowNum();
        try {
            //当前excel解析行
            //读取参数对象
            if (ObjectFieldValidate.validateField(dataVO)) {
                LOGGER.info("当前读取行{},读取数据{}", currentRowNum, dataVO);
                applyInfos.add(dataVO);// 数据存储到list，供批量处理，或后续自己业务逻辑处理。
            }
        } catch (Exception e) {
            LOGGER.error("excel读取，第[{}]异常, message{}, {}", currentRowNum, e.getMessage(), e);
            analysisMap.put("" + "|" + currentRowNum, "第" + currentRowNum + "行读取出错");
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        applyInfos.forEach(s->{
            AutoTaskDataEntity taskDataEntity = new AutoTaskDataEntity();
            taskDataEntity.setUserName(s.getUserName());
            taskDataEntity.setAreaRoad(s.getAreaRoad());
            taskDataEntity.setCity(s.getCity());
            taskDataEntity.setAge(s.getAge());
            taskDataEntity.setProvince(s.getProvince());
            taskDataEntity.setEmail(s.getEmail());
            taskDataEntity.setPhone(s.getPhone());
            taskDataEntity.setIdCard(s.getIdCard());
            taskDataEntity.setSex(s.getSex());
            autoCreateDataService.insertData(taskDataEntity);
        });

    }

    public Map<String, Object> getAnalysisMap() {
        return analysisMap;
    }
}
