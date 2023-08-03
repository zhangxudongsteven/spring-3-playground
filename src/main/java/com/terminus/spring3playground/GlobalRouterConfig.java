package com.terminus.spring3playground;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author zhangxudongsteven@gmail.com
 * @date 2023-08-03
 */

@Slf4j
@Configuration
public class GlobalRouterConfig {

    @Value("${spring.application.name}")
    private String serviceName;
    @Value("${spring.application.version}")
    private String version;

    @Bean
    public RouterFunction<ServerResponse> globalRouter() {
        // 心跳接口
        return nest(path(""),
                route(GET("/status"), this::status)
                        .andRoute(GET("/check-mvc-router"), this::checkMappings));
    }

    /**
     * status heartbeat，仅用于运维监控接口
     *
     * @param request ServerRequest
     * @return ServerResponse
     */
    private Mono<ServerResponse> status(ServerRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("service_name", serviceName + "-" + version);
        params.put("host_name", getHostname());
        Map<String, Object> map = new HashMap<>();
        map.put("data", params);
        map.put("status", "ok");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(map));
    }

    private static final List<String> SUFFIX_FILTERS = Arrays.asList("-docker", "-production-backend");

    public static String getHostname() {
        String hostName;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
            for (String suffix : SUFFIX_FILTERS) {
                if (hostName.endsWith(suffix)) {
                    hostName = hostName.substring(0, hostName.length() - suffix.length());
                }
            }
        } catch (Exception var3) {
            hostName = "Can't resolved hostname.";
        }
        return hostName;
    }

    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * 检查 Spring MVC 的路由映射
     *
     * @param request ServerRequest
     * @return ServerResponse
     */
    private Mono<ServerResponse> checkMappings(ServerRequest request) {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo mapping = entry.getKey();
            HandlerMethod method = entry.getValue();

            System.out.println("Mapping: " + mapping.getPatternsCondition());
            System.out.println("Method: " + method.getMethod().getName());
        }

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).build();
    }
}
