package com.terminus.spring3playground.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author xdzhang@mobvoi.com
 * @date 2023-08-03
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "langchain_app")
public class AppDO {

    @Id
    @Column("id")
    private Integer id;
    @Column("app_key")
    private String appKey;
}
