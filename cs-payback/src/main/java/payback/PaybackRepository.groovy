package payback

import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository

@CompileStatic
interface PaybackRepository extends JpaRepository<Payback, String> {

    List<Payback> findByCustomerId(Long customerId)

}
