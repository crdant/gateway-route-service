# Spring Cloud Gateway Demonstration

A micro-services demonstration using Spring Cloud Gateway.

## Architecture

## Build

The code is set up as a series of Gradle modules, so it can 
be compiled from the root directory with 

```shell
$ ./gradlew assemble
```

## Run

The demo is configured to run locally or on Pivotal 
Applicaton service. A Kubernetes deployment is in the works.

### Run locally

The demo is designed to run locally either with or without 
an Internet connection. It also uses TLS for all connections,
which means you'll need certificates for the various service
endpoints. See [TLS](#TLS)

I'm assuming you're on a Mac and have already install a 
JDK and Homebrew.

1. If you're using `direnv` run `direnv allow`, otherwise 
source the `.envrc` file. This will add the `bin` subdirectory
to your path so you can run some convenience commands.

1. Install dependencies with `brew bundle`

1. Run a local [UAA](https://github.com/cloudfoundry/uaa) server
with TLS. The default getting started instructions don't use TLS,
but it's fairly straightforard. Drop in [my updated `build.gradle`](https://gist.github.com/crdant/788fef3c59aba5544145321fbea811ed)
to get it started. You'll needs to update the hostname and
can if you want a port other than `9443` you can update that.

1. Set up authentication by running the `auth-setup.sh` script.

```shell
auth-setup.sh
Target set to https://uaa.local.crdant.io:9443/uaa
Access token successfully fetched and added to context.
{
  "id": "2a660876-bc42-41c7-9d51-086c466cc961",
  "meta": {
    "created": "2020-02-24T11:50:37.077Z",
    "lastModified": "2020-02-24T11:50:37.077Z"
  },
  "displayName": "greeter.greet",
  "zoneId": "uaa",
  "description": "Allow the greeter to say allow",
  "schemas": [
    "urn:scim:schemas:core:1.0"
  ]
}
The client gateway has been successfully created.
{
  "client_id": "gateway",
  "authorized_grant_types": [
    "authorization_code",
    "refresh_token"
  ],
  "redirect_uri": [
    "https://gateway.local.crdant.io:8443/login/oauth2/code/*"
  ],
  "scope": [
    "openid",
    "profile",
    "greeter.greet",
    "email"
  ],
  "resource_ids": [
    "none"
  ],
  "authorities": [
    "uaa.resource"
  ],
  "autoapprove": [],
  "lastModified": 1582563037430
}
{
  "id": "8235a75b-baf8-42f0-8e98-68294e8f8b5e",
  "meta": {
    "created": "2020-02-24T11:50:38.327Z",
    "lastModified": "2020-02-24T11:50:38.327Z"
  },
  "userName": "crdant",
  "name": {
    "familyName": "D'Antonio",
    "givenName": "Chuck"
  },
  "emails": [
    {
      "value": "cdantonio@pivotal.io",
      "primary": false
    }
  ],
  "groups": [
    {
      "value": "d4386f26-cce8-4e3b-9ca9-56a184a297b4",
      "display": "uaa.offline_token",
      "type": "DIRECT"
    },
    {
      "value": "e0788560-dfd6-4c3d-8beb-4fc30bb1cc99",
      "display": "password.write",
      "type": "DIRECT"
    },
    {
      "value": "a444d2a5-cdde-4681-b392-c247220570b6",
      "display": "cloud_controller.write",
      "type": "DIRECT"
    },
    {
      "value": "ca3b8bb4-ac24-4bbd-9ce8-01d72e575210",
      "display": "user_attributes",
      "type": "DIRECT"
    },
    {
      "value": "a0945480-cbba-47be-a30a-28959b2bb617",
      "display": "cloud_controller.read",
      "type": "DIRECT"
    },
    {
      "value": "12f57b33-8bb6-4d6d-b005-06f84733e5b4",
      "display": "oauth.approvals",
      "type": "DIRECT"
    },
    {
      "value": "a8a5f329-d7cb-4df4-aa1a-600d6e4f3559",
      "display": "cloud_controller_service_permissions.read",
      "type": "DIRECT"
    },
    {
      "value": "4b1d826d-f3e0-4a96-b763-d1a4c134acaa",
      "display": "approvals.me",
      "type": "DIRECT"
    },
    {
      "value": "956c98f2-f727-4dab-9c2e-3da06a62daa7",
      "display": "roles",
      "type": "DIRECT"
    },
    {
      "value": "7c3bd265-b8ea-4bf1-ab2a-c5522c1d8b66",
      "display": "openid",
      "type": "DIRECT"
    },
    {
      "value": "6a8f029f-50fd-4226-8045-99a3e5a98548",
      "display": "profile",
      "type": "DIRECT"
    },
    {
      "value": "80d95b4a-a1bf-4717-8013-6fee99cb9f08",
      "display": "scim.me",
      "type": "DIRECT"
    },
    {
      "value": "d9b68580-699f-4601-96f2-d4f69f30369d",
      "display": "uaa.user",
      "type": "DIRECT"
    },
    {
      "value": "2f4f0713-1c51-4938-97da-6dad09f6f075",
      "display": "scim.userids",
      "type": "DIRECT"
    }
  ],
  "active": true,
  "verified": true,
  "origin": "uaa",
  "zoneId": "uaa",
  "passwordLastModified": "2020-02-24T11:50:38.000Z",
  "schemas": [
    "urn:scim:schemas:core:1.0"
  ]
}
User $USER successfully added to group greeter.greet
```

The script sets up the UAA server for the demo, including creating a user for you
using your username on your machine and the password `springboot`.

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

1. The application is ready to run, you can go to the various endpoints on the 
Gateway and it will prompt you to log in, then forward your traffic to the Greeter
service.

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