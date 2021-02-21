package top.goodz.future.service;

import top.goodz.future.service.model.request.UploadObjectRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * minio服务接口
 * 2020.4.29
 */
public interface MinioService {
    /**
     *   上传
     * @param request
     * @return
     */

    String upload(UploadObjectRequest request);

    /**
     * 图片预览
     * @param key
     * @param response
     */
    void getView(String key, HttpServletResponse response);
}
