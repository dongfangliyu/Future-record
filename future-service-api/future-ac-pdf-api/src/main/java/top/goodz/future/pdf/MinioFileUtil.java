package top.goodz.future.pdf;

import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.goodz.future.feigin.IFutureFileClient;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 *  * @description： 启动实例化
 *  * @throws 
 *  * @author  YaJun.Zhang
 *  * @createTime：2020/5/5
 *  * @version：  1.0
 *
 * @return  
 */
@Component
public class MinioFileUtil {


    public static IFutureFileClient fileClient;


    @Autowired
    public void setOssConfMapper(IFutureFileClient fileClient) {
        MinioFileUtil.fileClient = fileClient;
    }

}
