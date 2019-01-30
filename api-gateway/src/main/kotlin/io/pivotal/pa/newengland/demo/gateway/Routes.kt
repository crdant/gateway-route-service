package io.pivotal.pa.newengland.demo.gateway

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory




@Configuration
class KotlinRoutes {
    @Autowired
    lateinit private var tokenRelay: TokenRelayGatewayFilterFactory


    @Bean("KotlinRoutes")
    fun routes(routeLocatorBuilder: RouteLocatorBuilder): RouteLocator =
            routeLocatorBuilder.routes {
                route {
                    path("/kotlin/**")
                    filters { stripPrefix(1) }
                    uri("http://httpbin.org")
                }
                route {
                    path("/portuguese")
                    uri( "lb://greeter")
                    filters {
                        setPath("/greeting/portuguese").filter(tokenRelay.apply())
                    }
                }
            }
}

