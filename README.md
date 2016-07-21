CAS Overlay Template
============================

Generic CAS version 5 maven war overlay using esup-otp-api

Official documentation : https://apereo.github.io/cas/development/

# Requirements
* JDK 1.8+

# Configuration

configuration files is src/main/webapp/WEB-INF/classes/application.properties

# Build

```bash
mvnw clean package
```

or

```bash
mvnw.bat clean package
```

# Deployment

## Embedded Tomcat

* Create a keystore file `thekeystore` under /etc/cas. Use the password `changeit` for both the keystore and the key/certificate entries.
* Ensure the keystore is loaded up with keys and certificates of the server.

Then, run:
```bash
java -jar target/cas.war
```

CAS will be available at:

* `http://cas.server.name:8080/cas`
* `https://cas.server.name:8443/cas`

## External
Deploy resultant `target/cas.war` to a Servlet container of choice.

esup-otp-api must be running to work, if you use CAS in a secure mode (HTTPS) you have to use a reverse proxy between http://localhost:3000 and https://localhost:3443