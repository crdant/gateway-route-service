package io.pivotal.pa.newengland.demo.gateway

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class KotlinRoutes {
    @Bean("KotlinRoutes")
    fun routes(routeLocatorBuilder: RouteLocatorBuilder): RouteLocator =
            routeLocatorBuilder.routes {
                route {
                    path("/kotlin/**")
                    filters { stripPrefix(1) }
                    uri("http://httpbin.org")
                }
                route {
                    path("/hiya")
                    uri( "lb://hello")
                    filters {
                        setPath("/greeting/english")
                    }
                }
            }
}