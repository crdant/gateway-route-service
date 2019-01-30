package io.pivotal.pa.newengland.demo.reactiveworldlyhello

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.MediaType.*
import org.springframework.web.reactive.function.BodyExtractors.toMono
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Hooks
import reactor.core.publisher.Mono

@Configuration
class Greeter () {
  @Value("\${defaultLanguage}")
  lateinit var defaultLanguage: String

  @Autowired
  lateinit var repository: GreetingRepository

  fun defaultLangauge(request: ServerRequest): Mono<ServerResponse> {
    return ServerResponse
      .ok()
      .contentType(MediaType.TEXT_PLAIN)
      .body(
        BodyInserters.fromObject("The default language is ${defaultLanguage}")
      )
  }

  fun greet(request: ServerRequest): Mono<ServerResponse> {
    val language = request.pathVariable("language")
    val greeting = repository.findByLanguage(if (language.length != 0) language else defaultLanguage);

    return ServerResponse
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(
        BodyInserters.fromPublisher(greeting, Greeting::class.java)
      )
  }

  fun learn(request: ServerRequest): Mono<ServerResponse> {
    val greeting = request.bodyToMono(Greeting::class.java);
    return ServerResponse
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(
        BodyInserters.fromPublisher(repository.saveAll(greeting), Greeting::class.java)
      )
  }
}


@Configuration
class GreetingRouter {

  @Autowired
  lateinit var greeter : Greeter

  @Bean
  fun router() = router {
    (accept(TEXT_HTML) or accept(TEXT_PLAIN)).nest {
      GET("/", greeter::defaultLangauge)
      POST("/greetings", greeter::learn)
      GET("/greeting/{language}", greeter::greet)
    }
  }

}
