package top.goodz.future.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.goodz.future.application.process.DataClearService;
import top.goodz.future.response.CommonResponse;
import top.goodz.future.utils.redis.RedisLock;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

@RestController
@RequestMapping("/api")
public class DataClearListenerEvent {

    @Autowired
    private DataClearService dataClearService;

    @Autowired
    private RedisTemplate redisTemplate;

    private static  final  String timeskey ="times_task_key";
    @GetMapping("/clear")
    public CommonResponse dataClear() {
        RedisLock lock = new RedisLock(redisTemplate,timeskey , 10000, 20000);
        try {
            if (lock.lock()) {
                long[] times = {30 * 1000, 50 * 1000, 90 * 1000}; // 时间轮
                Arrays.stream(times).forEach(s->{
                    timerTest(s);
                });

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            String currentValueStr = lock.get(timeskey + "_lock");
            if (currentValueStr != null ) {
                lock.unlock();
            }
        }


        return CommonResponse.isSuccess();
    }


    public void timerTest(long times) {
        //创建一个定时器
        Timer timer = new Timer("test--task:" + times+ ":");
        //schedule方法是执行时间定时任务的方法
            timer.schedule(new TimerTask() {

                //run方法就是具体需要定时执行的任务
                @Override
                public void run() {
                    System.out.printf("-----" + Thread.currentThread().getName() + "time:" + System.currentTimeMillis());
                    dataClearService.doClear(); //调用触发器

                    timer.cancel();
                }
            }, times, times);

    }
}
