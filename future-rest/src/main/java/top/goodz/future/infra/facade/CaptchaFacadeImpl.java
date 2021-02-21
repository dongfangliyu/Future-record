package top.goodz.future.infra.facade;

import org.springframework.stereotype.Service;
import top.goodz.future.feigin.IFutureFileClient;

import javax.annotation.Resource;

/**
 *  @Description TODO
 *  @Author Yajun.Zhang
 *  @Date 2020/8/9 18:08
 */
@Service
public class CaptchaFacadeImpl {

    @Resource
    private IFutureFileClient  iFutureFileClient;



}
