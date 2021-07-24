package top.goodz.future.domian.facade;

import top.goodz.future.infra.feigin.model.FutureEmailRequest;

/**
 * @Description EmailValidtionFacade
 * @Author Yajun.Zhang
 * @Date 2021/7/11 16:11
 */

public interface EmailValidationFacade {


    void send(FutureEmailRequest request);
}
