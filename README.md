# spring-auth-api-example

### An simple example of REST API using the following technologies.

- Spring MVC
- Spring Security
- JWT
- JPA
- HSQLDB
- JUnit
- Travis CI (http://travis-ci.org)
- Google Container Engine (https://cloud.google.com/container-engine)
- Kubernets (http://kubernetes.io)

To run this example, execute the following command on your console:
```shell
git clone https://github.com/jairelton/spring-auth-api-example.git
cd spring-auth-api-example
gradle assemble
java -jar ./build/libs/spring-auth-api-example-1.0.0.jar
```

The following endpoints would be available:

- POST /users
- PUT /users/:userId
- GET /users/:userId
- POST /login

Some endpoints require authorization, use the "Authorization" http header to pass the JWT token returned by the /login endpoint.

See the detailed documentation on https://app.swaggerhub.com/api/jairelton/spring-auth-api-example/1.0.0


