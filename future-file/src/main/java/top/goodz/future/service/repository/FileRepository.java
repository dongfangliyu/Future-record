package top.goodz.future.service.repository;

import java.io.InputStream;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */
public interface FileRepository {
    InputStream loadStream(String s);
}
