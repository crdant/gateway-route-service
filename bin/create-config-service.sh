cf create-service p-config-server standard api-gateway-config -c '
    {
        "git": {
            "uri": "https://github.com/crdant/gateway-routes",
            "searchPaths": "config"
        }
    }'
