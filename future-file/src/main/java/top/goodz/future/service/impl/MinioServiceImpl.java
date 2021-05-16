package top.goodz.future.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.goodz.future.enums.ExceptionCode;
import top.goodz.future.exception.ServiceException;
import top.goodz.future.service.model.request.UploadMateData;
import top.goodz.future.service.model.request.UploadObjectRequest;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.service.MinioService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.Location;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2020/5/2 14:22
 */
@Service
public class MinioServiceImpl implements MinioService {

    private static final Logger logger = LoggerFactory.getLogger(MinioServiceImpl.class);

    @Autowired
    private MinioClient minioClient;


    @Override
    public String upload(UploadObjectRequest request) {
        logger.info("图片公共上传 service接口请求入参request{}", request);
        uploadCheck(request);
        UploadMateData mateDate = request.getMateDate();
        PutObjectOptions options = request.getMateDate().getOptions();

        try {
            minioClient.putObject(request.getBucketName(), request.getKey(), getaInputStream(request), options);
        } catch (Exception e) {
            //catch (NoSuchAlgorithmException | InvalidKeyException | InternalException | IOException | XmlParserException e) {
            e.printStackTrace();
            throw new ServiceException(ExceptionCode.DEF_ERROR);
        }

        return request.getKey();
    }

    @Override
    public void getView(String key, HttpServletResponse response) {
        logger.info("图片预览 service接口请求入参key{}", key);
        Assert.notNull(key, "key can not be null");
        InputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {
            String aCase = key.substring(key.lastIndexOf("-") + 1, key.length()).toLowerCase();
            if ("jpg,jepg,gif,png".contains(aCase)) {//图片类型
                response.setContentType("image/" + aCase + ";charset=utf-8");
            } else if ("pdf".contains(aCase)) {//pdf类型
                response.setContentType("application/" + aCase);
            } else if ("mp4".contains(aCase)) {//MP4
                response.setContentType("video/mp4");
            } else {//自动判断下载文件类型

            }

            inputStream = minioClient.getObject(key.substring(0, key.indexOf("-")), key);
            outputStream = response.getOutputStream();
            //定义缓冲区
            byte[] buf = new byte[1024 * 1024 * 50];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();

        } catch (Exception e) {
            logger.error("图片预览 service 接口异常 e{}", e.getMessage());
            e.printStackTrace();

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private InputStream getaInputStream(UploadObjectRequest request) {
        InputStream inputStream = null;
        try {
            inputStream = Objects.isNull(request.getInputStream()) ? null : request.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorCodeEnum.ERROR.throwEcxeption();
        }
        return inputStream;
    }

    private void uploadCheck(UploadObjectRequest request) {
        Assert.notNull(request, "file upload param can not be null");
        Assert.notNull(request.getBucketName(), "bucketName can not be null ");
        Assert.notNull(request.getMateDate(), "matedata can not be null");
        Assert.notNull(request.getMateDate().getContentLength(), "ContentLength not be null");
        Assert.notNull(request.getMateDate().getContentType(), "ContentType can not be null");
    }
}
