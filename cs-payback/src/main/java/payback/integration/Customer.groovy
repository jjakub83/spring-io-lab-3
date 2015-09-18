package payback.integration

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.springframework.data.jpa.domain.AbstractPersistable

import javax.persistence.Entity

@ToString
@CompileStatic
class Customer {

    Long id

    String firstName

    String lastName

    String creditCard

}
