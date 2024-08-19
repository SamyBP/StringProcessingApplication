#!/bin/bash

CONFIG_FILE=Nginx/nginx.conf

echo "upstream backend {" > $CONFIG_FILE

for (( i=1; i<=$SERVER_REPLICAS_COUNT; i++ ))
do
    echo "    server stringprocessingapplication-server-$i:8080;" >> $CONFIG_FILE
done

cat <<EOF >> $CONFIG_FILE
}

server {
    listen 80;

    location / {
        proxy_pass http://backend/;
    }
}
EOF
