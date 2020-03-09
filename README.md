# Spring Cloud Gateway Demonstration

A micro-services demonstration using Spring Cloud Gateway.

## Build

The code is set up as a series of Gradle modules, so it can 
be compiled from the root directory with 

```shell
$ ./gradlew assemble
```

## Run

The demo is configured to run locally, on Kubernets, or on Tanzu 
Applicaton Service (or another Cloud Foundry runtime).

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
auth-setup
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
$ create-greeting https://greeter.local.crdant.io:7443

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

### Running on Kubernetes

#### Prerequisites

To use the demo as is, you will need to prepare your cluster by installing a couple
of components that are not in this repository.  I've included a setup script to run
a Minikube cluster with all of the prerequisities installed. To create that cluster 
locally run 

```shell
$ minikube-setup
```

and the cluster `gateway-demo` will be configured and ready for you to use.

If you're using your own cluster, you'll want to makes sure that both [cert-manager]
(https://cert-manager.io/) and [kpack](https://github.com/pivotal/kpack) are installed. 
Both can be installed from online YAMLs, so running 

```shell
$ kubectl apply --validate=false -f https://github.com/jetstack/cert-manager/releases/download/v0.13.1/cert-manager.yaml
$ kubectl apply -f https://github.com/pivotal/kpack/releases/download/v0.0.6/release-0.0.6.yaml
```

### Running the demo

To run the demo on Kubernetes, install the components of the architecture in
layers starting with some supporting elements. Before doing that, you'll need to
create a series of secrets for use by the other components. I haven't committed 
my secrets file, because it's, well, secret.

The secrets you'll need are:

| Secret  | Content |
| ------------- | ------------- |
| gcp-dns-credentials  | A Google private key JSON for a service account that can manipulate DNS. This is used by Cert Manager for the Let's Encrypt DNS challenge. It presumes that you're using Google for DNS of your cluster's inbound subdomain. You'll need a different secret and to edit `issuer.yml` if that's not the case.  |
| postgres-auth | Contains one data field, `POSTGRES_PASSWORD` which will be the password 
that postgres uses for it's admin password |
| registry-credentials | A secret of type `kubernetes.io/basic-auth` that contains a username and password for your image registry. Currently presumes Docker Hub.

Create all the secrets into the `demo` namespace.

1. After the secrets are created, create the cluster certificate issuer that uses 
Let's Encrypt to create TLS certificates for your services.

```shell
$ kubectl apply -f k8s/issuer.yml
```

2. Create a `kpack` cluster builder to build images used in the demo.

```shell
$ kubectl apply -f k8s/kpack.yml
```

3. Build the images

```shell
$ kubectl apply -f k8s/images.yml
```

this will take a little while. You may want to run 

```shell
$ watch kubectl get pods,image
```

to watch the building process and confirm when the image build has completed

4. Create the MongoDB appplication database and Postgres DB used by UAA.

```shell
$ kubectl apply -f k8s/mongodb.yml -f k8s/postgres.yml
```

5. Create the supporting services: UAA for authentication and authorization, Eureka for 
service discovery, and Spring Cloud Config Server for configuration.

```shell
$ kubectl apply -f k8s/uaa.yml -f k8s/eureka.yml -f k8s/configserver.yml
```

6. Configure UAA for authentication 

```shell
$ auth-setup
```

this will configure UAA for use by the application, including creating a user for
you with your local username and the password `springboot`.

7. Deploy the API gateway and the service it mediates.

```shell
$ kubectl apply -f k8s/api-gateway.yml -f k8s/hello-service.yml
```

8. Populate the database of the Hello World service.

```shell
$ create-greetings
```

### Running on Tanzu Application Service

I've created the manifests to deploy the Microservices and a couple of scripts to 
simplify creating the supporting infrastructure. You'll need to use your own TAS 
foundation in order to access the Single Sign-On service, since it's not available on
Pivotal Web Services. More detail instructions to follow.

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
