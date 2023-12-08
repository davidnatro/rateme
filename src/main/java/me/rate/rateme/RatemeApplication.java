package me.rate.rateme;

import java.math.BigInteger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@EnableWebSecurity
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class RatemeApplication {

  public static void main(String[] args) {
    SpringApplication.run(RatemeApplication.class, args);
  }
}
