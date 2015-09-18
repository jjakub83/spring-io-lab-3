package auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.security.Principal

@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
class CsAuthApplication {

    static void main(String[] args) {
        SpringApplication.run CsAuthApplication, args
    }
}

@RestController
class PrincipalController {

    @RequestMapping('/user')
    Principal currentUser(Principal principal) {
        return principal
    }
}

@Configuration
@EnableAuthorizationServer
class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager

    @Override
    void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .authenticationManager(authenticationManager)
    }

    @Override
    void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient('springio')
            .secret('secret')
            .authorizedGrantTypes('authorization_code', 'refresh_token', 'password')
            .scopes('openid');
    }
}
