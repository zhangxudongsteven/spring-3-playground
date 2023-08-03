# spring-3-playground

## MySQL R2DBC

- 使用 java.time.Instant 代替 java.sql.Timestamp
- JDK 17 下，继续使用 jakarta.persistence-api，包括但不限于 @GeneratedValue 等
- JDK 17 下，@Id 与 @Table 需要使用如下：
  - import org.springframework.data.annotation.Id; 
  - import org.springframework.data.relational.core.mapping.Table;
- 对于大字符串，不必保留 jakarta @Lob 标签

参考资料：
- Code Demo：https://gatheca-george.medium.com/spring-webflux-using-relational-database-mysql-postgresql-fcc5e487f57f

## WebFlux

- 优先使用 Router Function 模式；使用 MVC Controller 模式也可，不需要引入 web 包，但不推荐。

参考资料：
- 课程代码：https://github.com/sunxpin/spring-webflux