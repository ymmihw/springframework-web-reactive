### Start the app with

```
mvn spring-boot:run
```

### Start the backing service


```
mvn spring-boot:run
```

### Gatling target

target the 'baseUrl' in BootLoadSimulation.scala to the MVC or Webflux Server

### Run Gatling

```
mvn gatling:test -D SIM_USERS=5000
```