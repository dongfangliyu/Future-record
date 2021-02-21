package top.goodz.future.request.email;

import lombok.Data;
import top.goodz.future.request.FutureEmailData;

/**
 * 邮箱请求参数
 * @author zhangyajun
 */
@Data
public class EmailRequest  {
	

	private static final long serialVersionUID = 1L;

	/**
	 * 发送email请求参数
	 */
	private EmailData data;
	
	/**
	 * 接口名
	 */
	private String channelsName;
	
	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 邮件ID, 用来多线程发送邮件时作为区分标识
	 */
	private String emailId;

	/**
	 * 仲裁邮箱数据
	 */
	private FutureEmailData futureEmailData;

}
