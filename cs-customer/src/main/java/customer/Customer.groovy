package customer

import groovy.transform.CompileStatic
import org.springframework.data.jpa.domain.AbstractPersistable

import javax.persistence.Entity

@Entity
@CompileStatic
class Customer extends AbstractPersistable<Long> {

    String firstName

    String lastName

    String creditCard

    @Override
    public void setId(Long id) {
        super.setId(id)
    }
}