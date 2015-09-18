package payback.integration;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.Application;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class IntegrationClient {

    private final CustomerClient customerClient;
    private final MerchantClient merchantClient;

    @Autowired
    public IntegrationClient(CustomerClient customerClient, MerchantClient merchantClient) {
        this.merchantClient = merchantClient;
        this.customerClient = customerClient;
    }

    @HystrixCommand(fallbackMethod = "findBustomerByIdFallback")
    public Optional<Customer> findBustomerById(Long customerId) {
        return of(customerClient.findById(customerId));
    }

    Optional<Customer> findBustomerByIdFallback(Long customerId) {
        return empty();
    }

    @HystrixCommand(fallbackMethod = "findCustomerByCreditCardFallback")
    public Optional<Customer> findCustomerByCreditCard(String creditCardNumber) {
        return of(customerClient.findByCreditCardNumber(creditCardNumber));
    }

    Optional<Customer> findCustomerByCreditCardFallback(String creditCardNumber) {
        return empty();
    }

    @HystrixCommand(fallbackMethod = "findMerchantByIdFallback")
    public Optional<Merchant> findMerchantById(Long metchantId) {
        return of(merchantClient.findOne(metchantId));
    }

    Optional<Merchant> findMerchantByIdFallback(Long metchantId) {
        return empty();
    }

}

@Order(1)
@Component
class DiscoveryClientExample implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DiscoveryClientExample.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public void run(String... strings) throws Exception {
        try {
            log.info("------------------------------");
            log.info("DiscoveryClient Example");

            discoveryClient.getAllKnownRegions()
                    .forEach((String region) -> log.info("Region: " + region));

            Application customerServiceApp = discoveryClient.getApplication("cs-customer");
            Application merchantServiceApp = discoveryClient.getApplication("cs-merchant");

            log.info("Customer service: " + customerServiceApp);
            log.info("Merchant service: " + merchantServiceApp);

            log.info("------------------------------");
        } catch (Exception e) {
            log.error("DiscoveryClient Example Error!", e);
        }
    }
}

@Order(2)
@Component
class RestTemplateExample implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateExample.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(String... strings) throws Exception {
        try {
            log.info("------------------------------");
            log.info("RestTemplate Example");

            ParameterizedTypeReference<List<Customer>> responseType =
                    new ParameterizedTypeReference<List<Customer>>() {
                    };

            ResponseEntity<List<Customer>> exchange = this.restTemplate.exchange(
                    "http://cs-customer/customers",
                    HttpMethod.GET, null, responseType);

            exchange.getBody().forEach(it -> log.info("Customer: " + it));

            log.info("------------------------------");
        } catch (Exception e) {
            log.error("RestTemplate Example Error!", e);
        }
    }
}

@Order(3)
@Component
class FeignExample implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(FeignExample.class);

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private MerchantClient merchantClient;

    @Override
    public void run(String... strings) throws Exception {
        try {
            log.info("------------------------------");
            log.info("Feign Example");

            log.info("Customer: " + customerClient.findById(1L));
            log.info("Merchant: " + merchantClient.findOne(1L));
            log.info("------------------------------");
        } catch (Exception e) {
            log.error("Feign Example Error!", e);
        }
    }
}
