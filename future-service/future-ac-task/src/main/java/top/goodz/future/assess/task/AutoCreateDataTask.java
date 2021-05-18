package top.goodz.future.assess.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;
import top.goodz.future.application.process.DataClearProcessService;

import javax.annotation.Resource;

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
