package top.goodz.future.feigin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.goodz.future.config.FeignConfig;
import top.goodz.future.response.CommonResponse;

/**
 *  * @Description:  文件远程调用api
 *  * @throws 
 *  * @author zhangyajun
 *  * @createTime： 20200505
 *  * @version： 1.0
 *  
 */

@FeignClient(value = "future-file",
        url = "127.0.0.1:8081", configuration = FeignConfig.class)
public interface IFutureFileClient {


    /**
     *  * @description：图片上传通用接口
     *  * @throws 
     *  * @author zhangyajun
     *  * @createTime：2020/5/5
     *  * @version：  1.0
     *
     * @return  
     */
    @RequestMapping(value = "/minio/upLoad", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResponse upLoad(@RequestParam("key") String key, @RequestPart(value = "file", required = false) MultipartFile file);

    @GetMapping("/minio/list")
    CommonResponse list(@RequestParam String param);


}
