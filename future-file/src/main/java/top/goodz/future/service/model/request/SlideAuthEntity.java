package top.goodz.future.service.model.request;

import lombok.Data;

/**
 * @Description 滑动图片实体
 * @Author Yajun.Zhang
 * @Date 2020/6/12 23:22
 */
@Data
public class SlideAuthEntity {

    private String cutoutImage;

    private String originImage;

    private String shadeImage;

    private int x;

    private int y;

    private String uuidOne;

    private String id;

    private Long expireTimestemp;

    private String status;

}
