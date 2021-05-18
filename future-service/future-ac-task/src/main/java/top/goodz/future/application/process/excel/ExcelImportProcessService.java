package top.goodz.future.application.process.excel;/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.goodz.future.domain.listener.TaskDataImportListener;

/**
 *  @Description TODO
 *  @Author Yajun.Zhang
 *  @Date 2021/5/18 22:15
 */
@Service
public class ExcelImportProcessService {


    @Autowired
    private TaskDataImportListener taskDataImportListener;


}
