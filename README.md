Web Application G16 Repository

## DOCUMENTATION:
Can be found in the folder "WA-G16.ticketing.documentation":
   
    API_documentation.json
    Data Layer Design.mdj

## RUN THE SERVER
- [in the file "WA-G16\ticketing\src\main\resources\application.properties" set:]

      spring.jpa.hibernate.ddl-auto=create

Build the docker image for spring:
- enter in the folder: .../WA-G16/ticketing
- [build:]

      on linux: ./gradlew
            [on windows: gradlew.bat]
- build server docker image with jib:

      ./gradlew jibDockerBuild
    

Docker: postgress runs on port: 5432, keycloak on port: 8080, Server (named:ticketing) run on port: 8081, commands to run:
- [ensure to be logged in Docker:]

      docker login

- Open a terminal in the folder "WA-G16.ticketing.docker" and execute the command:

      docker compose up
      
- wait untill the process ends.
- [default configuration (setted in file: WA-G16\ticketing\docker\.env):]

      POSTGRESQL_DB=postgres
      POSTGRESQL_USER=postgres
      POSTGRESQL_PASSWORD=postgres

      KEYCLOAK_ADMIN=admin
      KEYCLOAK_ADMIN_PASSWORD=admin

## RUN THE TEST
Enter in the folder: .../WA-G16/ticketing
-execute: 
        
    linux: ./gradlew test --tests "it.polito.wa2.ticketing.TicketingApplicationTests"
    windows: gradlew.bat test --tests "it.polito.wa2.ticketing.TicketingApplicationTests"
    
## OTHER RESOURCES
Can be found "WA-G16.ticketing.src.main.resources":
        
- customers.csv
- employees.csv
- products.csv

### Keycloak users:
- auto-imported realm name: ticketing
- users are imported automatically

| username  | password |
|-----------|----------|
| manager   | password |
| expert1   | password |
| expert2   | password |
| customer1 | password |
| customer2 | password |
