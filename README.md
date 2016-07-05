CAS Overlay Template
============================

Generic CAS maven war overlay to exercise the latest versions of CAS. This overlay could be freely used as a starting template for local CAS maven war overlays. The CAS services management overlay is available [here](https://github.com/Jasig/cas-services-management-overlay).

# Versions
```xml
<cas.version>5.0.0</cas.version>
```

# Requirements
* JDK 1.8+

# Configuration

The `etc` directory contains the configuration files that need to be copied to `/etc/cas`.

Current files are:

* `cas.properties`
* `log4j2.xml`

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
