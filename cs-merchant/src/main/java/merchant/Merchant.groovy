package merchant

import groovy.transform.CompileStatic
import org.springframework.data.jpa.domain.AbstractPersistable

import javax.persistence.Entity
import javax.persistence.OneToOne

import static javax.persistence.CascadeType.ALL

@Entity
@CompileStatic
class Merchant extends AbstractPersistable<Long> {

    String name

    BigDecimal percentage

    BigDecimal minAmount

    BigDecimal maxPayback

    @Override
    public void setId(Long id) {
        super.setId(id)
    }
}
