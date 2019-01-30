package io.pivotal.pa.newengland.demo.reactiveworldlyhello

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans

@SpringBootApplication
class ReactiveWorldlyHelloApplication

fun main(args: Array<String>) {
	runApplication<ReactiveWorldlyHelloApplication>(*args)
}

val beans = beans {
	bean<Greeter>()
	bean<GreetingRouter>()
	bean<GreetingRepository>()
}