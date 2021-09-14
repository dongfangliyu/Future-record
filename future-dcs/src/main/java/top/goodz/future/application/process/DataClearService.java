package top.goodz.future.application.process;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import top.goodz.future.infra.model.DataClearEvent;

import javax.annotation.Resource;

@Service
@Slf4j
public class DataClearService {

    @Resource
    private ApplicationContext applicationContext;

    public void doClear(){
        // todo 具体业务逻辑
        //定义出触发器
        DataClearEvent clearEvent = new DataClearEvent(this, "业务逻辑");

        applicationContext.publishEvent(clearEvent);
    }
}
