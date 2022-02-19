
package top.goodz.future.gateway.dynamic;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.alibaba.nacos.NacosConfigProperties;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 动态路由监听器
 *
 * @author zhangyajun
 */
@Order
@Slf4j
@Component
public class DynamicRouteServiceListener implements CommandLineRunner {

    private String DATA_ID = "future-gateway-routes";

    private final DynamicRouteService dynamicRouteService;
    private final NacosDiscoveryProperties nacosDiscoveryProperties;
    private final NacosConfigProperties nacosConfigProperties;

    public DynamicRouteServiceListener(DynamicRouteService dynamicRouteService, NacosDiscoveryProperties nacosDiscoveryProperties, NacosConfigProperties nacosConfigProperties) {
        this.dynamicRouteService = dynamicRouteService;
        this.nacosDiscoveryProperties = nacosDiscoveryProperties;
        this.nacosConfigProperties = nacosConfigProperties;
        dynamicRouteServiceListener();
    }

    /**
     * 监听Nacos下发的动态路由配置
     */
    private void dynamicRouteServiceListener() {
        try {
            Properties properties = new Properties();

            String dataId = DATA_ID;
            String group = nacosConfigProperties.getGroup();
            String serverAddr = nacosDiscoveryProperties.getServerAddr();
            properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
            properties.put(PropertyKeyConst.NAMESPACE, "");
            ConfigService configService = NacosFactory.createConfigService(properties);
            String config = configService.getConfig(dataId, group, 5000);
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    try {
                        List<RouteDefinition> routeDefinitions = JSON.parseArray(configInfo, RouteDefinition.class);
                        log.info("-------future-gateway-routes loading success  config info:{} ",config);
                        for (RouteDefinition rote : routeDefinitions) {
                            dynamicRouteService.save(rote);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("保存Nacos下发的动态路由配置 error {}",e.getMessage());
                    }
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException ignored) {
            log.error("监听Nacos下发的动态路由配置 error {}",ignored.getMessage());
        }
    }


    @Override
    public void run(String... args) throws Exception {
        dynamicRouteServiceListener();
    }
}
