package top.goodz.future.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.response.CommonResponse;

import javax.validation.ValidationException;


/**
 * 全局异常
 *
 * @author zhangyajun
 */
@ControllerAdvice
public class GlobalExceptionHandler   {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private MessagesSourceResolver messagesSourceResolver;


	/*@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseBody
	public CommonResponse<Object> MethodArgumentNotValidHandler(HttpServletRequest request,
																MethodArgumentNotValidException exception) throws Exception {
		// 按需重新封装需要返回的错误信息
		List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();
		// 解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
		for (FieldError error : exception.getBindingResult().getFieldErrors()) {
			ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
			invalidArgument.setDefaultMessage(error.getDefaultMessage());
			invalidArgument.setField(error.getField());
			invalidArgument.setRejectedValue(error.getRejectedValue());
			invalidArguments.add(invalidArgument);
		}
		return new CommonResponse.Builder<Object>(false, CommonUtil.listToString(invalidArguments),
				ConstantsEnum.STATUS_INPUT_ERROR.getCode(), null).build();
	}*/

    @ExceptionHandler
    @ResponseBody
    public CommonResponse<Object> Exception(Exception ex) {
        // 根据不同错误转向不同页面
        if (ex instanceof NullPointerException) {
            logger.error(ex.getMessage(), ex);
            return messagesSourceResolver.errorCodeTransfer( new CommonResponse.Builder<Object>(false, ErrorCodeEnum.ERROR.getMessage(),
                    ErrorCodeEnum.ERROR.getCode(), ex.getMessage()).build(),null,null);
        } else if (ex instanceof IllegalArgumentException) {
            logger.error(ex.getMessage(), ex);
            return new CommonResponse.Builder<Object>(false, ErrorCodeEnum.ERROR.getMessage(),
                    ErrorCodeEnum.ERROR.getCode(), null).build();
        } else if (ex instanceof ServiceException) {
            logger.error(ex.getMessage(), ex);
            return messagesSourceResolver.errorCodeTransfer(new CommonResponse.Builder<Object>(false, ex.getMessage(),
                    ((ServiceException) ex).getErrorCode(),null).build(),null,null);
        } else if (ex instanceof CommonException) {
            logger.error(ex.getMessage(), ex);
            return  messagesSourceResolver.errorCodeTransfer(new CommonResponse.Builder<Object>(false, ex.getMessage(),
                    ((CommonException) ex).getErrorId(), null).build(),null,null);
        } else if (ex instanceof ValidationException){
            CommonException cause = (CommonException) ex.getCause();
            return  messagesSourceResolver.errorCodeTransfer(new CommonResponse.Builder<Object>(false, cause.getErrorMessgee(),
                    cause.getErrorId(), null).build(),null,null);
        }else {
            logger.error(ex.getMessage(), ex);
            return messagesSourceResolver.errorCodeTransfer(new CommonResponse.Builder<Object>(false, ErrorCodeEnum.ERROR.getMessage(),
                    ErrorCodeEnum.ERROR.getCode(), ex.getMessage()).build(),null,null);
        }
    }
}
