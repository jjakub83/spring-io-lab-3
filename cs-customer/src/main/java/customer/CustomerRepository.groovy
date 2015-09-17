package customer

import groovy.transform.CompileStatic
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

@CompileStatic
interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByCreditCard(String cardNo)

    Page<Customer> findByFirstNameAndLastName(
        String first, String last, Pageable pageable)

}
