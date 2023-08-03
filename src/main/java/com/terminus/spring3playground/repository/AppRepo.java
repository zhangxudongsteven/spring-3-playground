package com.terminus.spring3playground.repository;

import com.terminus.spring3playground.model.AppDO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author xdzhang@mobvoi.com
 * @date 2023-08-03
 */

public interface AppRepo extends ReactiveCrudRepository<AppDO, Integer> {}
