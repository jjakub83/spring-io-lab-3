package payback.integration;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import groovy.transform.CompileStatic;

@FeignClient("cs-customer")
@CompileStatic
public interface CustomerClient {

    @RequestMapping(value = "/customers/{id}", method = GET)
    Customer findById(@PathVariable("id") Long customerId);

    @RequestMapping(value = "/customers/findByCreditCard/{number}", method = GET)
    Customer findByCreditCardNumber(
            @PathVariable("number") String creditCardNumber);

}
