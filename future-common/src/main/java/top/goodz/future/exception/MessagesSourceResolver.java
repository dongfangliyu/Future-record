package top.goodz.future.exception;


import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import top.goodz.future.response.CommonResponse;

@Component
public class MessagesSourceResolver {

    private MessageSource messageSource;

    public MessagesSourceResolver(MessageSource messageSource) {
        this.messageSource = messageSource;

    }


    public CommonResponse errorCodeTransfer(CommonResponse commonResponse, Object[] args, String message) {

        String sourceMessage = messageSource.getMessage(commonResponse.getCode(), args, message, LocaleContextHolder.getLocale());

        commonResponse.setMsg(sourceMessage);

        return commonResponse;

    }

}
