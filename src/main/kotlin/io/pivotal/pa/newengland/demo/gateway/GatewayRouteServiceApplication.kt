package io.pivotal.pa.newengland.demo.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient

class GatewayRouteService

fun main(args: Array<String>) {
	runApplication<GatewayRouteService>(*args)
}
