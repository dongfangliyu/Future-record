package top.goodz.future.domain.config.support;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import top.goodz.future.domain.constant.ConstantsOuath;
import top.goodz.future.utils.ChkUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 自定义token异常输出
 * @author zhangyajun
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthExceptionEntryPoint.class);
	
//	@Autowired
//	private RedisUtil redisUtil;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws ServletException {
		String headToken = request.getHeader(ConstantsOuath.OAUTH_TOKEN_KEY);
		JSONObject json = new JSONObject();
		if (ChkUtil.isNotEmpty(headToken) && headToken.contains(ConstantsOuath.ACCESS_TOKEN_START_PORTION)) {
			headToken = headToken.replace(ConstantsOuath.ACCESS_TOKEN_START_PORTION, "");
		}
//		if (ChkUtil.isNotEmpty(redisUtil.get(headToken))) {
//			json.put("code", ConstantsEnum.LOGIN_ERROR_ACCESS_TOKEN_REPEAT.getCode());
//			json.put("msg", MessageFormat.format(ConstantsEnum.LOGIN_ERROR_ACCESS_TOKEN_REPEAT.getMsg(), redisUtil.get(headToken)));
//		} else {
			json.put("code","401");
			json.put("msg", "无权访问future 系统");
//		}
		json.put("error", "401");
		json.put("exceptionMessage", authException.getMessage());
		json.put("path", request.getServletPath());
		json.put("timestamp", String.valueOf(new Date().getTime()));
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getOutputStream(), json);
		} catch (Exception e) {
			LOGGER.error("AuthExceptionEntryPoint异常：" + e.getMessage(), e);
			throw new ServletException();
		}
	}
}