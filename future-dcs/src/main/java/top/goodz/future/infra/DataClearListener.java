package top.goodz.future.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.infra.model.DataClearEvent;

/**
 * 监听器
 */
@Component
@Slf4j
public class DataClearListener implements ApplicationListener<DataClearEvent> {

    @Override
    public void onApplicationEvent(DataClearEvent dataClearEvent) {

        System.out.println("this my listener envet:" + dataClearEvent.getName());

    }
}
