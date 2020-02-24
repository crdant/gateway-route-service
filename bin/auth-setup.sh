#!/usr/bin/env bash
set -eu

uaa target https://uaa.local.crdant.io:9443/uaa
uaa get-client-credentials-token admin -s adminsecret

uaa create-group greeter.greet -d "Allow the greeter to say hello"

uaa create-client gateway -s grapheme-evoke-moonbeam-manque \
    --authorized_grant_types authorization_code \
    --scope openid,email,profile,greeter.greet \
    --authorities uaa.resource \
    --redirect_uri 'https://gateway.local.crdant.io:8443/login/oauth2/code/*'

uaa create-user $USER  \
    --password "springboot" \
    --email "$(git config --get user.email)" \
    --givenName "$(git config --get user.name | cut -f 1 -d' ')" \
    --familyName "$(git config --get user.name | cut -f 2 -d' ')" \

uaa add-member greeter.greet crdant