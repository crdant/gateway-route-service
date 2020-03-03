#!/usr/bin/env bash
set -eu

uaa target https://uaa.minikube.crdant.io/
uaa get-client-credentials-token admin -s adminsecret

uaa create-group greeter.greet -d "Allow the greeter to say hello"

uaa create-client gateway -s grapheme-evoke-moonbeam-manque \
    --authorized_grant_types authorization_code \
    --scope openid,email,profile,greeter.greet \
    --authorities uaa.resource \
    --redirect_uri 'https://gateway.minikube.crdant.io/login/oauth2/code/*'

uaa create-user $USER  \
    --password "springboot" \
    --email "$(git config --get user.email)" \
    --givenName "$(git config --get user.name | cut -f 1 -d' ')" \
    --familyName "$(git config --get user.name | cut -f 2 -d' ')" \

uaa add-member greeter.greet crdant
