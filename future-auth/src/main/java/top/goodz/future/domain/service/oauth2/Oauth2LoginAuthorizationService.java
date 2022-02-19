package top.goodz.future.domain.service.oauth2;


import com.alibaba.fastjson.JSON;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import top.goodz.future.assess.model.UserAuthLoginRequest;
import top.goodz.future.domain.constant.ConstantsOuath;
import top.goodz.future.domain.model.vo.UserAuthLoginVO;
import top.goodz.future.domain.model.vo.token.OauthSecurityConfig;
import top.goodz.future.domain.utils.TokenUtil;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.utils.ChkUtil;
import top.goodz.future.utils.HttpCloseableUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
 * 对oauth2 扩展点封装
 */
@Component
public class Oauth2LoginAuthorizationService {
    @Autowired
    private OauthSecurityConfig oauthSecurityConfig;

    @Autowired
    private TokenEndpoint tokenEndpoint;

    public UserAuthLoginVO password(UserAuthLoginRequest userAuthLogin) {
        UserAuthLoginVO response = new UserAuthLoginVO();
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("username", userAuthLogin.getUsername()));
        list.add(new BasicNameValuePair("password", userAuthLogin.getPassword()));
        list.add(new BasicNameValuePair("grant_type", ConstantsOuath.OAUTH_GRANT_TYPE_PWD));
        list.add(new BasicNameValuePair("scope", ConstantsOuath.OAUTH_SCOPES));
        list.add(new BasicNameValuePair("client_id", ConstantsOuath.OAUTH_WITHCLIENT));
        list.add(new BasicNameValuePair("client_secret", ConstantsOuath.OAUTH_SECRET));

        try {
            String result = HttpCloseableUtils.doPostNameValuePairAddHeader(oauthSecurityConfig.getAccessTokenUrl(), list, TokenUtil.USER_CODE_HEADER_KEY, "future-user");
            response = JSON.parseObject(result, UserAuthLoginVO.class);

            if (ChkUtil.isEmpty(response.getUsername()) || ChkUtil.isEmpty(response.getAccess_token())) {
                ErrorCodeEnum.PASSWORD_OR_ACCOUNT_ERROR.throwEcxeption();
            }
        } catch (IOException e) {
            e.printStackTrace();
            ErrorCodeEnum.PASSWORD_OR_ACCOUNT_ERROR.throwEcxeption();
        }
        return response;

    }


    public Map code(HttpServletResponse httpServletResponse) {

        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("response_type", "code");
        map.put("scope", ConstantsOuath.OAUTH_SCOPES);
        map.put("client_id", ConstantsOuath.OAUTH_WITHCLIENT);


        Map loginResponse = null;
        String result = null;
        result = HttpCloseableUtils.sendGet("http://localhost:8081/oauth/authorize", map);

        try {
            if (result.contains("-1")) {
                httpServletResponse.sendRedirect("http://localhost:8081/login");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        loginResponse = JSON.parseObject(result, Map.class);

        return loginResponse;

    }

    /**
     * 授权码获取token
     *
     * @param code
     * @param
     * @return
     */
    public OAuth2AccessToken token(String code) {

        OAuth2AccessToken oAuth2AccessToken = null;

        //创建客户端信息,客户端信息可以写死进行处理，因为Oauth2密码模式，客户端双信息必须存在，所以伪装一个
        //如果不想这么用，需要重写比较多的代码
        User clientUser = new User(ConstantsOuath.OAUTH_WITHCLIENT, ConstantsOuath.OAUTH_SECRET, new ArrayList<>());
        //生成已经认证的client
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(clientUser, null, new ArrayList<>());
        Map<String, String> parameters = new HashMap<String, String>();
        //放入验证码
        parameters.put("code", code);
        parameters.put("grant_type", ConstantsOuath.AUTHORIZATION_CODE);
        try {
            oAuth2AccessToken = tokenEndpoint.postAccessToken(token, parameters).getBody();
        } catch (HttpRequestMethodNotSupportedException e) {
            ErrorCodeEnum.PASSWORD_OR_ACCOUNT_ERROR.throwEcxeption();
        }

        return oAuth2AccessToken;


    }


}
