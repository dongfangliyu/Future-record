package top.goodz.future.domain.listener.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 自动生成数据实体
 * @Author Yajun.Zhang
 * @Date 2020/7/7 23:05
 */

@Data
public class AutoTaskDataVO extends BaseRowModel implements Serializable {


    @ExcelProperty(index = 0)
    private String userName;

    @ExcelProperty(index = 1)
    private String phone;

    @ExcelProperty(index = 2)
    private String email;

    @ExcelProperty(index = 3)
    private String idCard;

    @ExcelProperty(index = 4)
    private String sex;

    @ExcelProperty(index = 5)
    private Integer age;

    @ExcelProperty(index = 6)
    private String province;

    @ExcelProperty(index = 7)
    private String city;

    @ExcelProperty(index = 8)
    private String areaRoad;


}
