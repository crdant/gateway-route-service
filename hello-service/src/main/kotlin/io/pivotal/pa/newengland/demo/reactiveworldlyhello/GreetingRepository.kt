package io.pivotal.pa.newengland.demo.reactiveworldlyhello


import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.data.annotation.Id
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories



data class Greeting (
  @Id
  val id: Int? = null,
  val language: String? = null,
  val text: String? = null
)

@Repository
interface GreetingRepository : ReactiveCrudRepository<Greeting, String> {
  fun findByLanguage(language: String): Mono<Greeting>
}

@EnableReactiveMongoRepositories
class MongoReactiveApplication : AbstractReactiveMongoConfiguration() {

  @Bean
  override fun reactiveMongoClient(): MongoClient {
    return MongoClients.create()
  }

  override fun getDatabaseName(): String {
    return "greetings"
  }
}
