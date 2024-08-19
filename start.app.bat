set SERVER_REPLICAS_COUNT=1
set WORKER_REPLICAS_COUNT=1

sh Nginx\build.config.sh

docker compose up -d %1