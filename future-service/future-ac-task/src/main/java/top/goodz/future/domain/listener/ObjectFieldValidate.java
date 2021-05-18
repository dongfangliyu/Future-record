package top.goodz.future.domain.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.goodz.future.utils.ChkUtil;

import java.lang.reflect.Field;

public class ObjectFieldValidate {

	public static final Logger logger = LoggerFactory.getLogger(ObjectFieldValidate.class);

	/**
	 * 检查指定对象的属性值是否为空，排除指定的属性值
	 * @Description: 
	 * @param object
	 * @author zhangyajun
	 * @createTime：
	 */
	public static boolean validateField(Object object) {
		boolean target = false;
		for (Field f : object.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			try {
				// 判断属性名称是否在排除属性值中
				// 判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
				if ("serialVersionUID".equals(f.getName())){
					continue;
				}
				if (ChkUtil.isNotEmpty(f.get(object))) {
					target = true;
					break;
				}
			} catch (IllegalArgumentException e) {
				target = false;
				logger.error("对象属性解析异常" + e.getMessage());
				return target;
			} catch (IllegalAccessException e) {
				target = false;
				logger.error("对象属性解析异常" + e.getMessage());
				return target;
			}
		}
		return target;
	}

	/**
	 * 检查指定对象的属性值是否全部非空，排除指定的属性值
	 * @param object
	 * @author zhangyajun
	 * @createTime：
	 */
	public static boolean validateFieldNoEmpty(Object object) {
		boolean target = true;
		for (Field f : object.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			try {
				// 判断属性名称是否在排除属性值中
				// 判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
				if ("serialVersionUID".equals(f.getName())){
					continue;
				}
				if (ChkUtil.isEmpty(f.get(object))) {
					target = false;
					break;
				}
			} catch (IllegalArgumentException e) {
				target = false;
				logger.error("对象属性解析异常" + e.getMessage());
				return target;
			} catch (IllegalAccessException e) {
				target = false;
				logger.error("对象属性解析异常" + e.getMessage());
				return target;
			}
		}
		return target;
	}
}
