package merchant

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.rest.SpringBootRepositoryRestMvcConfiguration
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.stereotype.Component

import static java.math.MathContext.DECIMAL32

@SpringBootApplication
@EnableDiscoveryClient
class CsMerchantApplication {

    static void main(String[] args) {
        SpringApplication.run CsMerchantApplication, args
    }
}

@Configuration
class RepositoryRestMvcConfigurer extends SpringBootRepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        super.configureRepositoryRestConfiguration(config)
        config.exposeIdsFor(Merchant)
    }
}

@Component
class DbPopulator implements CommandLineRunner {

    @Autowired MerchantRepository repository

    @Override
    void run(String... args) throws Exception {
        if (repository.findAll().isEmpty()) {
            repository.save(
                new Merchant(
                    name: 'Guns&Bullets',
                    percentage: new BigDecimal('0.05', DECIMAL32),
                    minAmount: new BigDecimal('100', DECIMAL32),
                    maxPayback: new BigDecimal('50', DECIMAL32)))
        }
    }
}

//@Component
//class DbPopulator implements CommandLineRunner {
//
//    @Autowired MerchantRepository repository
//    @Autowired PaybackPolicyRepository policyRepository
//
//    @Override
//    void run(String... args) throws Exception {
//        if (repository.findAll().isEmpty()) {
//            policyRepository.save(
//                new PaybackPolicy(
//                    merchant: repository.save(new Merchant(name: 'Guns&Bullets')),
//                    percentage: new BigDecimal('0.05', DECIMAL32),
//                    minAmount: new BigDecimal('100', DECIMAL32),
//                    maxPayback: new BigDecimal('50', DECIMAL32))
//            )
//        }
//    }
//}
