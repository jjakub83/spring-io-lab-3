package customer

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import static java.util.Optional.ofNullable

@Service
@CompileStatic
class CustomerService {

    private final CustomerRepository repository

    @Autowired
    CustomerService(CustomerRepository repository) {
        this.repository = repository
    }

    List<Customer> findAll() {
        return repository.findAll()
    }

    Optional<Customer> findOne(Long id) {
        return ofNullable(repository.findOne(id))
    }

    Customer findByCreditCard(String cardNo) {
        return ofNullable(repository.findByCreditCard(cardNo))
            .orElseThrow({ new CustomerNotFound(cardNo) })
    }

    Customer create(Customer customer) {
        return repository.save(customer)
    }
}

class CustomerNotFound extends RuntimeException {

    CustomerNotFound(Long id) {
        super("Customer with ID $id not found!")
    }

    CustomerNotFound(String cardNo) {
        super("Customer with card number $cardNo not found!")
    }
}
