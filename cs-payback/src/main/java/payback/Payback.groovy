package payback

import org.springframework.data.jpa.domain.AbstractPersistable

import javax.persistence.Embedded
import javax.persistence.Entity

@Entity
class Payback extends AbstractPersistable<Long> {

    Long customerId;

    BigDecimal amount;

    @Embedded
    Purchase purchase;

    public void setId(Long id) {
        super.setId(id);
    }
}
