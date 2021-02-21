package top.goodz.future.application.process;

import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.goodz.future.domain.service.AutoCreateDataService;
import top.goodz.future.domain.service.entity.AutoTaskDataEntity;
import top.goodz.future.domain.task.AutoCreateDataServiceTask;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2020/7/31 23:44
 */
@Service
public class DataClearProcessService {

    private final Logger log = LoggerFactory.getLogger(DataClearProcessService.class);
    @Autowired
    private AutoCreateDataService autoCreateDataService;


    public void dataCleaning() {


        ExecutorService executor = Executors.newFixedThreadPool(5);
        //清洗身份证号不合格用户
        executor.execute(() -> idCardLenghtClean());

        //清洗年龄不合格用户
        executor.execute(() -> ageClean());

        // 清洗身份证重复数据
        executor.execute(() -> idCardRepeatClean());

        // 关闭线程池
        executor.shutdown();
    }

    private void idCardRepeatClean() {
        log.info("清洗身份证重复数据任务开始-->{}", LocalDate.now());
        autoCreateDataService.idCardRepeatClean();
    }

    private void ageClean() {
        log.info("年龄数据清洗任务开始-->{}", LocalDate.now());
        autoCreateDataService.ageClean(18, 65); // 18-65 岁合格
    }

    private void idCardLenghtClean() {
        log.info("身份证数据清洗任务开始-->{}", LocalDate.now());
        autoCreateDataService.idCardLenghtClean();
    }

    public void createData() {
        XxlJobLogger.log("自动生成数任务开始");
        // 创建一个线程池, 使用有界的任务队列
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        // 定义线程返回值集合
        List<Future<AutoTaskDataEntity>> resultList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            Future<AutoTaskDataEntity> entityFuture = executor.submit(new AutoCreateDataServiceTask());

            resultList.add(entityFuture);
        }

        for (Future<AutoTaskDataEntity> future : resultList) {
            try {
                AutoTaskDataEntity emailResponse = future.get();
                if (!Objects.isNull(emailResponse)) {
                    System.out.println(Thread.currentThread().getName() + "-->" + emailResponse);
                    autoCreateDataService.insertData(emailResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 关闭线程池
        executor.shutdown();
    }

}
