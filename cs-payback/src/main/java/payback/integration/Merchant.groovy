package payback.integration

import groovy.transform.ToString
import payback.Purchase

import static java.math.BigDecimal.ZERO
import static java.math.MathContext.DECIMAL32

@ToString
class Merchant {

    Long id

    String name

    BigDecimal percentage

    BigDecimal minAmount

    BigDecimal maxPayback

    BigDecimal paybackFor(Purchase purchase) {
        return Optional.of(purchase)
            .filter { it -> percentage != null && it.getAmount() != null }
            .filter { it ->  minAmount == null || minAmount.compareTo(it.getAmount()) <= 0 }
            .map { it -> percentage.multiply(it.getAmount(), DECIMAL32) }
            .map { it -> maxPayback != null && maxPayback.compareTo(it) < 0 ? maxPayback : it }
            .orElse(ZERO);
    }
}
