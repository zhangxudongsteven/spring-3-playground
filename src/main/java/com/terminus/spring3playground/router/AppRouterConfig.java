package com.terminus.spring3playground.router;

import com.terminus.spring3playground.handler.AppHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author xdzhang@mobvoi.com
 * @date 2023-08-03
 */

@Slf4j
@Configuration
public class AppRouterConfig {

    @Resource
    AppHandler appHandler;

    @Bean
    public RouterFunction<ServerResponse> appRouter() {
        // 心跳接口
        return nest(path("/router/app"), route(GET("/{app_id}"), appHandler::getAppById));
    }
}
