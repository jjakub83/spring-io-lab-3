package customer

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletResponse

import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.ResponseEntity.notFound
import static org.springframework.http.ResponseEntity.ok
import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@CompileStatic
class CustomerController {

    private final CustomerService service

    @Autowired
    CustomerController(CustomerService service) {
        this.service = service
    }

    @RequestMapping(value = '/', method = GET,
        produces = APPLICATION_JSON_VALUE)
    List<Customer> findAll() {
        return service.findAll()
    }

    @RequestMapping(value = '/{id}', method = GET,
        produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Customer> findOne(Long id) {
        return service.findOne(id)
            .map({ ok(it) })
            .orElseGet({ notFound().build() })
    }

    @RequestMapping(value = '/findByCreditCard/{cardNo}', method = GET,
        produces = APPLICATION_JSON_VALUE)
    Customer findByCreditCard(String cardNo) {
        return service.findByCreditCard(cardNo)
    }

//    @ResponseStatus(CREATED)
//    @RequestMapping(value = '/', method = POST,
//        consumes = APPLICATION_JSON_VALUE)
//    ResponseEntity<Void> create(@RequestBody Customer customer) {
//        Customer saved = service.create(customer)
//        created(linkTo(methodOn(CustomerController)
//            .findOne(saved.id))
//            .toUri()).build()
//    }

    @ExceptionHandler(CustomerNotFound)
    void handleCustomerNotFound(CustomerNotFound e, HttpServletResponse response) {
        response.sendError(NOT_FOUND.value(), e.message)
    }

}

@ControllerAdvice
class BusinessExceptionsHandler {

    @ExceptionHandler(CustomerNotFound)
    @ResponseStatus(NOT_FOUND)
    void simplyHandleCustomerNotFound() {
    }
}
