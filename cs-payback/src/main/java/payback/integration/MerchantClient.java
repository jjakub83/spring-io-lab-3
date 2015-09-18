package payback.integration;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("cs-merchant")
public interface MerchantClient {

    @RequestMapping(value = "/merchants/{id}", method = GET)
    Merchant findOne(@PathVariable("id") Long id);

}
