package io.pivotal.pa.newengland.demo.gateway

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory

@SpringBootApplication
@EnableDiscoveryClient
class Gateway

@Autowired
private val filterFactory: TokenRelayGatewayFilterFactory? = null

fun main(args: Array<String>) {
	runApplication<Gateway>(*args)
}

