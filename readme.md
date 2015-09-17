System Requirements:

http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-system-requirements

Kolejność konfiguracji:

http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config

Prezentacja Sam'a Newman'a:
https://vimeo.com/105751281


CreditCard:
 String number

Customer:
 String firstName
 String lastName
 Set<CreditCard> creditCards

Wyszukanie klienta po numerze karty:
GET /byCreditCard/{number}


Asercje z JSON-path:

```
<dependency>
    <groupId>com.jayway.jsonpath</groupId>
    <artifactId>json-path</artifactId>
    <scope>test</scope>
</dependency>
```

Teach a dog to REST:
https://vimeo.com/17785736


CRUD do Customer'a.
Walidacja firstName i lastName po blackliście z properies'ów (@ExceptionHandler).


```
@Bean
public EnvironmentRepository environmentRepository() {
	return new NativeEnvironmentRepository(environment);
}
```


```
@Component
class DbPopulator implements CommandLineRunner {

    @Autowired CustomerRepository repository

    @Override
    void run(String... args) throws Exception {
        if (repository.findAll().isEmpty()) {
            repository.save([
                new Customer(
                    firstName: 'John',
                    lastName: 'Smith',
                    creditCard: '9876543212345678'),
                new Customer(
                    firstName: 'Jane',
                    lastName: 'Smith',
                    creditCard: '9876678998766789')])
        }
    }
}
```


```
class PaybackPolicy {
	BigDecimal percentage
    BigDecimal minAmount
    BigDecimal maxPayback
}
class Merchant {
	String name
	PaybackPolicy paybackPolicy
}
```


```
@Configuration
class RepositoryRestMvcConfigurer extends SpringBootRepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        super.configureRepositoryRestConfiguration(config)
        config.exposeIdsFor(Merchant)
    }
}
```



```
class Purchase {
    BigDecimal amount
    String creditCardNumber
    Long merchantId
}

class Payback {
	String id
	Long customerId
	BigDecimal amount
	Purchase purchase
}
```

