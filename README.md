# Spring Cloud Gateway Demonstration

A micro-services demonstration using Spring Cloud Gateway.

## Architecture

## Build

The code is set up as a series of Gradle modules, so it can 
be compiled from the root directory with 

```shell
> ./gradlew assemble
```

## Run

The demo is configured to run locally or on Pivotal 
Applicaton service. A Kubernetes deployment is in the works.

### Run locally

The demo is designed to run locally either with or without 
an Internet connection. It also uses TLS for all connections,
which means you'll need certificates for the various service
endpoints. See [SSL](#SSL)

1. Run a local [UAA](https://github.com/cloudfoundry/uaa) server
with SSL. (TODO: explicit instructions)
1. In the root directory of this project, run local versions
of the Eureka and Config Server services using the 
[Spring Cloud CLI](https://cloud.spring.io/spring-cloud-cli/reference/html/).

```shell
$ spring cloud eureka configserver
```

2. 

### Run on Pivotal Web Services


### Run on your own Pivotal Application Service foundation


## SSL

I use [Let's Encrypt](https://letsencrypt.org) and a wildcard
DNS entry that points to `127.0.0.1` to easily create and 
maintain certificates for local demo environments.

In my case, I have the domain `crdant.io` maintained with
Google Cloud DNS and an `A` record `*.local.crdant.io` that
points to `127.0.0.1`. I can create or renew this cert with

```shell
$ export DOMAIN=local.crdant.io
$ certbot certonly --server https://acme-v02.api.letsencrypt.org/directory \
    --certname ${DOMAIN} --domain '*.${DOMAIN}' \
    --config-dir /usr/local/etc/certbot \
    --work-dir /usr/local/var/certbot \
    --logs-dir /usr/local/var/log \
    --dns-google --dns-google-propagation-seconds 120 \
    --dns-google-credentials $GOOGLE_CREDENTIALS_JSON
```

and then create a PKCS12 file for it

```shell
$ export KEY_FILE=/usr/local/etc/certbot/live/${DOMAIN}/privkey.pem
$ export CERT_FILE=/usr/local/etc/certbot/live/${DOMAIN}/fullchain.pem
$ openssl pkcs12 -export -name local -in $CERT_FILE -inkey $KEY_FILE \
    -out /usr/local/etc/certbot/live/${DOMAIN}/certificate.p12
```