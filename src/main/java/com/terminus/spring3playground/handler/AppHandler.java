package com.terminus.spring3playground.handler;

import com.terminus.spring3playground.model.AppDO;
import com.terminus.spring3playground.repository.AppRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author xdzhang@mobvoi.com
 * @date 2023-08-03
 */

@Slf4j
@Component
public class AppHandler {

    private final AppRepo appRepo;

    public AppHandler(AppRepo appRepo) {
        this.appRepo = appRepo;
    }

    public Mono<ServerResponse> getAppById(ServerRequest request) {
        Integer appId = Integer.valueOf(request.pathVariable("app_id"));
        log.info("get app by id: {}", appId);
        return appRepo.findById(appId).flatMap(appDO -> ServerResponse.ok().body(Mono.just(appDO), AppDO.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
