package io.pivotal.pa.newengland.demo.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaRoutes {

    @Autowired
    private TokenRelayGatewayFilterFactory filterFactory;

    @Bean("JavaRoutes")
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route( r -> r.path("/esperanto")
                    .filters(f -> f.filter(filterFactory.apply()))
                    .uri("lb://greeter")
                )
                .build();
    }

}
