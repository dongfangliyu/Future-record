
package top.goodz.future.gateway.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import top.goodz.future.gateway.dynamic.DynamicRouteService;
import top.goodz.future.gateway.dynamic.GatewayPredicate;
import top.goodz.future.gateway.dynamic.GatewayRoute;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态路由端点
 *
 * @author zhangyajun
 */
@RestController
@AllArgsConstructor
@RequestMapping("/route")
public class RouteEndpoint {

    private DynamicRouteService dynamicRouteService;
    private RouteDefinitionLocator routeDefinitionLocator;

    @GetMapping("/list")
    public Flux<RouteDefinition> list() {
        return routeDefinitionLocator.getRouteDefinitions();
    }

    @PostMapping("/save")
    public String save(@RequestBody GatewayRoute gatewayRoute) {
        RouteDefinition definition = assembleRouteDefinition(gatewayRoute);
        return this.dynamicRouteService.save(definition);
    }

    @PostMapping("/update")
    public String update(@RequestBody GatewayRoute gatewayRoute) {
        RouteDefinition definition = assembleRouteDefinition(gatewayRoute);
        return this.dynamicRouteService.update(definition);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        return this.dynamicRouteService.delete(id);
    }

    private RouteDefinition assembleRouteDefinition(GatewayRoute gatewayRoute) {
        RouteDefinition definition = new RouteDefinition();
        List<PredicateDefinition> pdList = new ArrayList<>();
        definition.setId(gatewayRoute.getId());
        List<GatewayPredicate> gatewayPredicateDefinitionList = gatewayRoute.getPredicates();
        for (GatewayPredicate gpDefinition : gatewayPredicateDefinitionList) {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gpDefinition.getArgs());
            predicate.setName(gpDefinition.getName());
            pdList.add(predicate);
        }
        definition.setPredicates(pdList);
        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayRoute.getUri()).build().toUri();
        definition.setUri(uri);
        return definition;
    }

}
