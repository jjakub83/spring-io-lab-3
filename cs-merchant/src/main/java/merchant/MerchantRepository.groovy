package merchant

import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
@CompileStatic
interface MerchantRepository extends JpaRepository<Merchant, Long> {

}