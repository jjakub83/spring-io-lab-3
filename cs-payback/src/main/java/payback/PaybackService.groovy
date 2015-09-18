package payback

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus
import payback.integration.Customer
import payback.integration.IntegrationClient
import payback.integration.Merchant

import java.awt.print.Pageable

import static java.math.BigDecimal.ZERO
import static java.math.MathContext.DECIMAL32
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE

@Component
@CompileStatic
class PaybackService {

    private final PaybackRepository repository
    private final IntegrationClient integration

    @Autowired PaybackService(PaybackRepository repository, IntegrationClient integration) {
        this.repository = repository
        this.integration = integration
    }

    List<Payback> customerPaybacks(Long customerId) {
        return repository.findByCustomerId(customerId)
    }

    Payback registerPaybackFor(Purchase purchase) {
        Customer customer = integration
            .findCustomerByCreditCard(purchase.creditCardNumber)
            .orElseThrow { new PaybackUnavailable('Customer service not available!') }

        Merchant merchant = integration
            .findMerchantById(purchase.merchantId)
            .orElseThrow { new PaybackUnavailable('Merchant service not available!') }

        BigDecimal amount = merchant.paybackFor(purchase)

        return repository.save(new Payback(customerId: customer.id, amount: amount, purchase: purchase))
    }

    PaybackSummary summaryFor(Long customerId) {
        PaybackSummary summary = new PaybackSummary(customer: integration.findBustomerById(customerId)
            .orElse(new Customer(lastName: 'Customer data unavailable!')))

        repository.findByCustomerId(customerId).each {
            summary.amount = summary.amount.add(it.amount, DECIMAL32)
            if (!summary.merchants.collect { it.id } .contains(it.purchase.merchantId)) {
                summary.merchants.add(integration.findMerchantById(it.purchase.merchantId)
                    .orElse(new Merchant(name: 'Merchant data unavailable!')))
            }
        }
        return summary
    }

}

@CompileStatic
class PaybackSummary {

    Customer customer

    BigDecimal amount = ZERO

    Set<Merchant> merchants = []

}

@ResponseStatus(SERVICE_UNAVAILABLE)
class PaybackUnavailable extends RuntimeException {

    PaybackUnavailable(String msg) {
        super(msg)
    }
}
