package top.goodz.future.domain.service.impl;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Component
public class UserClientDetailServiceImpl extends JdbcClientDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public UserClientDetailServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 缓存客户端信息
     *
     * @param clientId 客户端id
     */
    @Override
    @SneakyThrows
    public ClientDetails loadClientByClientId(String clientId) {
        logger.info("-----JdbcClientDetailsService loadClientByClientId req:{} ", clientId);
        return super.loadClientByClientId(clientId);
    }

}
