package top.goodz.future.service.model.request;

import io.minio.PutObjectOptions;
import lombok.Data;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2020/5/2 21:49
 */
@Data
public class UploadMateData {

    private String contentType;

    private Long contentLength;

    private PutObjectOptions options;


}
