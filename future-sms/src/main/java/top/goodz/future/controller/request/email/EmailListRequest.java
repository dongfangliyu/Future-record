package top.goodz.future.controller.request.email;

import lombok.Data;
import top.goodz.future.controller.request.FutureEmailData;

import java.util.List;

/**
 * @author : Yajun.Zhang
 * @date : 2020-01-15 11:53
 * @Description :
 */
@Data
public class EmailListRequest {

    /**
     * 请求集合
     */
    private List<EmailRequest> emailRequests;

    /**
     * 所属仲裁委
     */
    private String olapName;


    private FutureEmailData futureEmailData;
}
