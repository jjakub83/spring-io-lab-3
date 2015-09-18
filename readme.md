Prezentacja:
https://docs.google.com/presentation/d/1lY6ucGmfQnB_TTFckNz4DV0iC91eragbazOG_hrpwZI/edit?usp=sharing

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



Stworzyć endpoint REST'owy, który przyjmuje
na wejściu obiekt `Purchase`, znajduje przy
pomocy feign-client'ów dane customer'a na
podstawie jego numeru karty kredytowej
oraz dane merchant'a na podstawie jego id,
a nstępnie wylicza kwotę payback'u przez
mnożenie kwoty transakcji i procentu payback'u
i zapisuje wynik w encji Payback w swojej
bazie danych. Drugi endpoint do listowania
zapisanych obiektów Payback.
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


https://github.com/spring-cloud-samples/authserver


http://localhost:8080/uaa/oauth/authorize?response_type=code&client_id=acme&redirect_uri=http://example.com
