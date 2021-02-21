package top.goodz.future.domian.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpLoadFileFeignReq  implements Serializable {


    private String key;

    private MultipartFile file;
}
