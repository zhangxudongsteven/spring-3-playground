package com.terminus.spring3playground.controller;


import com.terminus.spring3playground.model.AppDO;
import com.terminus.spring3playground.repository.AppRepo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 传统的Controller模式。推荐使用RouterFunction模式。
 *
 * @author xdzhang@mobvoi.com
 * @date 2023-08-03
 */

@Slf4j
@Deprecated
@RestController
@RequestMapping("/controller/app")
public class AppController {

    @Resource
    private AppRepo appRepo;

    @GetMapping("/{app_id}")
    public Mono<ResponseEntity<AppDO>> getApp(@PathVariable(name = "app_id") Integer appId) {
        return appRepo.findById(appId).flatMap(appDO -> {
            log.info("get app by id: {}", appId);
            log.info("app: {}", appDO);
            return Mono.just(new ResponseEntity<>(appDO, HttpStatus.OK));
        });
    }
}
