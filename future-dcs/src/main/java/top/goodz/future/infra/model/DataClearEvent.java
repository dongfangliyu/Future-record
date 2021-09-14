package top.goodz.future.infra.model;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * 事件
 */
public class DataClearEvent extends ApplicationEvent {

    private String name;

    public DataClearEvent(Object source,String name ) {
        super(source);
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
