set SERVER_REPLICAS_COUNT=1
set WORKER_V1_REPLICAS_COUNT=1
set WORKER_V2_REPLICAS_COUNT=2

sh Nginx\build.config.sh

docker compose up -d %1