package gateway

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.cloud.security.oauth2.sso.EnableOAuth2Sso
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableZuulProxy
class CsGatewayApplication {

    static void main(String[] args) {
        SpringApplication.run CsGatewayApplication, args
    }
}

@Configuration
@EnableOAuth2Sso
class SecurityConfig {

}
