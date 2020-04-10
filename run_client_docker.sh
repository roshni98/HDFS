#!/bin/bash
docker-compose down
docker pull minniemice18/distributed_hdfs
docker-compose up --detach
docker run -it --network=hadoopnet -v $(pwd)/output:/app/src/output/ -v $(pwd)/input:/app/src/input/ minniemice18/distributed_hdfs /app/run_docker.sh -i 