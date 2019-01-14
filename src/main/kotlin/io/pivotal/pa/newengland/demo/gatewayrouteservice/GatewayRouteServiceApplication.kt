package io.pivotal.pa.newengland.demo.gatewayrouteservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication(exclude = [ SecurityAutoConfiguration::class ] )
@EnableDiscoveryClient

class GatewayRouteService

fun main(args: Array<String>) {
	runApplication<GatewayRouteService>(*args)
}

