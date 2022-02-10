package top.goodz.future.infra.annotation;

import org.passay.*;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.exception.CommonException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;


/**
 * 自定义密码规则注解实现类
 * 采用 validator 验证框架 与 org 的 passay 结合
 *
 * zhangyajun
 */
class PasswordFormatValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        PasswordValidator validator = new PasswordValidator(Arrays.asList(

                new LengthRule(8, 16),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
                new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false),
                new WhitespaceRule()
        ));
        constraintValidatorContext.disableDefaultConstraintViolation();

        RuleResult ruleResult = validator.validate(new PasswordData(password));

        if (!ruleResult.isValid()){
            throw  new CommonException(ErrorCodeEnum.PASSWORD_FORMAT_ERROR);
        }

        return true;
    }
}
