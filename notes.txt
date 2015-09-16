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

<dependency>
    <groupId>com.jayway.jsonpath</groupId>
    <artifactId>json-path</artifactId>
    <scope>test</scope>
</dependency>




