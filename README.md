# EquitiesExchange

GOALS:
1) To try out an app generator. Was intending to use [JHipster](https://www.jhipster.tech/) or [JHipster Lite](https://www.jhipster.tech/jhipster-lite/), but ended up using [Bootify.io](https://bootify.io) instead.
2) To make something that I can eventually hook to my [Small Market Data project](https://github.com/mring33621/small-market-data-unaut)
3) Relearn some JPA and misc other things.

COMMENTARY:
1) It's a 'stock exchange' with a really stripped down data model
2) The home-built 'order matching engine' runs every 5 seconds and tries to match the oldest orders first. It supports only limit orders only and will perform partial fills, to the annoyance of everyone.
3) No, you probably shouldn't use JPA in an order matching engine, but it's just a toy.
4) No, you shouldn't base a stock exchange on the H2 database, but again, it's just a toy.
5) Bootify generated CRUD UI and REST endpoints for Customers, BuyOrders, SellOrders and Matches
6) I had to run a sed script to fix Bootify's poor pluralization of 'Matches' (it put 'Matchs'), but otherwise not too bad.
7) OK, one more criticism -- Bootify only made about 50% of available fields visible in the CRUD GET endpoints. I'd rather comment out extras than manually add missing data.
8) There is no security, KYC, money management or any features like that. I'll prob build a separate, stripped down 'broker' app for some of that.

----

This app was created with Bootify.io - more documentation [can be found here](https://bootify.io/docs/). Feel free to contact us for further questions.

## Development

During development it is recommended to use the profile `local`. In IntelliJ, `-Dspring.profiles.active=local` can be added in the VM options of the Run Configuration after enabling this property in "Modify options".

Update your local database connection in `application.yml` or create your own `application-local.yml` file to override settings for development.

In addition to the Spring Boot application, the DevServer must also be started. [Node.js](https://nodejs.org/) has to be available on the system - the latest LTS version is recommended. Only once the dependencies have to be installed:

```
npm install
```

The DevServer can now be started as follows:

```
npm run devserver
```

Using a proxy the whole application is now accessible under `localhost:8081`. All changes to the templates and JS/CSS files are immediately visible in the browser.

## Build

The application can be built using the following command:

```
gradlew clean build
```

Node.js is automatically downloaded using the `gradle-node-plugin` and the final JS/CSS files are integrated into the jar.

The application can then be started with the following command - here with the profile `production`:

```
java -Dspring.profiles.active=production -jar ./build/libs/equities-exchange-0.0.1-SNAPSHOT.jar
```

## Further readings

* [Gradle user manual](https://docs.gradle.org/)  
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)  
* [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)  
* [Thymeleaf docs](https://www.thymeleaf.org/documentation.html)  
* [Webpack concepts](https://webpack.js.org/concepts/)  
* [npm docs](https://docs.npmjs.com/)  
* [Tailwind CSS](https://tailwindcss.com/)  
