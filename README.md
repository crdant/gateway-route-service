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
endpoints. See [TLS](#TLS)

1. Run a local [UAA](https://github.com/cloudfoundry/uaa) server
with TLS. The default getting started instructions don't use TLS,
but it's fairly straightforard. Drop in [my updated `build.gradle`](https://gist.github.com/crdant/788fef3c59aba5544145321fbea811ed)
to get it started. You'll needs to update the hostname and
can if you want a port other than `9443` you can update that.


1. In the root directory of this project, run local versions
of the Eureka and Config Server services using the 
[Spring Cloud CLI](https://cloud.spring.io/spring-cloud-cli/reference/html/).

```shell
$ spring cloud eureka configserver
```

The terminal will display the expected Spring Boot logging
information until you terminate the servers.

3. Start the gateway application and supporting Hello World
service.

in one terminal:

```shell
$ cd hello-service
$ ../gradlew bootRun
```

in another terminal:

```shell
$ cd api-gateway
$ ../gradlew bootRun
```

Both terminals will show the Spring Boot logging output
until you terminate each one.

4. Load the test data in a third terminal:

```shell
$ create-greeting.sh https://greeter.local.crdant.io:7443

  {
    "id": 2,
    "language": "spanish",
    "text": "Hola"
  }
]
[
  {
    "id": 3,
    "language": "french",
    "text": "Bonjour"
  }
]
[
  {
    "id": 4,
    "language": "esperanto",
    "text": "Saluton"
  }
]
[
  {
    "id": 5,
    "language": "portuguese",
    "text": "Ola"
  }
]
```

### Run on Pivotal Web Services


### Run on your own Pivotal Application Service foundation


## TLS

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