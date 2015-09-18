package customer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
//import org.springframework.cloud.security.oauth2.sso.EnableOAuth2Sso
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableDiscoveryClient
//@EnableOAuth2Sso
class CsCustomerApplication {

    static void main(String[] args) {
        SpringApplication.run CsCustomerApplication, args
    }
}

@Component
class DbPopulator implements CommandLineRunner {

    @Autowired CustomerRepository repository

    @Override
    void run(String... args) throws Exception {
        if (repository.findAll().isEmpty()) {
            repository.save([
                new Customer(
                    firstName: 'John',
                    lastName: 'Smith',
                    creditCard: '9876543212345678'),
                new Customer(
                    firstName: 'Jane',
                    lastName: 'Smith',
                    creditCard: '9876678998766789')])
        }
    }
}


