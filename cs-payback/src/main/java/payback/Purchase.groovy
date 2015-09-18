package payback

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
@CompileStatic
class Purchase {

    @Column(name = "purchaseAmount")
    BigDecimal amount

    String creditCardNumber

    Long merchantId

}