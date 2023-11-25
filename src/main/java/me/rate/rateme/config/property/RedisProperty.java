package me.rate.rateme.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.redis")
public class RedisProperty {

  private String host;
  private String password;
  private Integer port;
  private Integer db;
  private Integer timeout;
  private Integer maxTotal;
  private Integer minIdle;
}
