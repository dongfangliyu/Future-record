package top.goodz.future.service.model.request;

import lombok.Data;

import java.io.InputStream;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2020/5/2 21:23
 */
@Data
public class UploadObjectRequest {


    private String bucketName;

    private String key;

    private InputStream inputStream;

    private UploadMateData mateDate;

 /*   private static  class  builder{


    }
*/

}
