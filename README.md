###
Main task:

    1. Update/Create entities in accordance with Hibernate ORM.
        
    2. Add new model UserAccount to store the amount of prepaid money the user has in the system, which should be used during booking procedure:
        a. Add methods for refilling the account to the BookingFacade
        b. Add Repository and Service objects
        c. Add ticketPrice field to Event entity

    3. Create DB schema for storing application entities. Provide script with schema creation DDL for DBMS

    4. Add inheritance to DAO objects from one of the Spring Data interfaces (CRUDRepository)
        a. DAO objects should store and retrieve application entities
        b. User transaction management where necessary
        c. Configure Hibernate for work with DBMS

    5. Update ticket booking methods to check and withdraw money from user account according to the ticketPrice for a particular event.

    6. Cover code with unit tests

    7. Cover Facade layer with integration tests

    8. Project should contain proper pre-configured logging using SLF4J

    9. Add Hibernate caching to all getById() methods. Add query caching. Show that your caches are really working in tests (cache hits count, cache  miss count)

Define before start: BookingFacade
###
To start using Java 17 add this param to jvm: "--add-opens java.base/java.time=ALL-UNNAMED"
