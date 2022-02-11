
package top.goodz.future.gateway.endpoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.jca.GetInstance;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务发现控制匹配
 *
 * @author zhangyajun
 */

@AllArgsConstructor
@Component
public class DiscoveryEndpointService {

    private final DiscoveryClient discoveryClient;

    /**
     * 获取服务实例
     */
    public ServiceEndpoint instances(URI uri) {

        if (null == uri) {
            return null;
        }

        String host = uri.getHost();

        String[] segments = host.split("/");

        String newHost = segments[0];


        List<List<ServiceInstance>> instancesList = getInstancesList();

        for (List<ServiceInstance> li : instancesList) {
            ServiceInstance serviceInstance = li.get(0);

            if (newHost.equals(serviceInstance.getServiceId())) {
                ServiceEndpoint endpoint = new ServiceEndpoint();
                endpoint.setServiceId(serviceInstance.getServiceId());
                endpoint.setHost(serviceInstance.getHost());
                endpoint.setPort(serviceInstance.getPort());

                return endpoint;
            }
        }
        return null;
    }


    private List<List<ServiceInstance>> getInstancesList() {
        List<List<ServiceInstance>> instances = new ArrayList<>();
        ;
        List<String> services = discoveryClient.getServices();
        services.forEach(s -> {
            List<ServiceInstance> list = discoveryClient.getInstances(s);
            instances.add(list);
        });
        return instances;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ServiceEndpoint {
        private String serviceId;

        private String host;

        private int port;
    }

}
