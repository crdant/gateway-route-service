package io.pivotal.pa.newengland.demo.gatewayrouteservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.route

@SpringBootApplication
class GatewayRouteService

fun main(args: Array<String>) {
	runApplication<GatewayRouteService>(*args)
}

@Configuration
class KotlinRoutes {
    @Bean
    fun routesInKotlin(routeLocatorBuilder: RouteLocatorBuilder): RouteLocator =
            routeLocatorBuilder.routes {
                route {
                    path("/kotlin/**")
                    filters { stripPrefix(1) }
                    uri("http://httpbin.org")
                }
            }
}