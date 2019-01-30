package io.pivotal.pa.newengland.demo.reactiveworldlyhello

import org.springframework.context.annotation.Bean
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity

@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  fun oauth2Security(http: ServerHttpSecurity): SecurityWebFilterChain {
    http
      .csrf().disable()
      .authorizeExchange().pathMatchers("/greetings", "/actuator/**").permitAll()
            .and()
            .authorizeExchange().pathMatchers("/greeting/**").hasAuthority("SCOPE_greeter.greet")
            .anyExchange().authenticated()
      .and()
      .oauth2ResourceServer()
            .jwt()

    return http.build()
  }
}
