package top.goodz.future.assets.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.goodz.future.application.process.DataClearProcessService;
import top.goodz.future.domain.service.AutoCreateDataService;
import top.goodz.future.domain.service.entity.AutoTaskDataEntity;
import top.goodz.future.domain.task.AutoCreateDataServiceTask;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @Description 数据生成, 清洗 task
 * @Author Yajun.Zhang
 * @Date 2020/7/7 22:31
 */
@Component
public class AutoCreateDataTask extends IJobHandler {

    @Resource
    private DataClearProcessService dataClearProcessService;


    @Override
    @XxlJob("autoCreateJobHandler")
    public ReturnT<String> execute(String s) throws Exception {
        dataClearProcessService.createData();
        return ReturnT.SUCCESS;
    }


    @XxlJob("dataCleaningTask")
    public ReturnT<String> dataCleaningTask(String s) {
        dataClearProcessService.dataCleaning();
        return ReturnT.SUCCESS;
    }
}
