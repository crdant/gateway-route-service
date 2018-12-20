package io.pivotal.pa.newengland.demo.gatewayrouteservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GatewayRouteService

fun main(args: Array<String>) {
	runApplication<GatewayRouteService>(*args)
}
