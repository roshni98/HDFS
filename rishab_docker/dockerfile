FROM openjdk:8-jdk-slim

WORKDIR /usr/app

# RUN apt-get update && apt-get install make

RUN ["apt-get", "update"]
RUN ["apt-get", "install", "make"]
RUN ["apt-get", "install", "-y", "vim"]

# RUN apt-get update && \
#     apt-get install -y openjdk-8-jdk && \
#     apt-get install -y ant && \
#     apt-get clean;

# # # Install Java
# RUN apt-get update && \
#     apt-get install -y openjdk-8-jdk && \
#     apt-get install -y ant && \
#     apt-get clean;

# # # Fix certificate issues
# RUN apt-get update && \
#     apt-get install ca-certificates-java && \
#     apt-get clean && \
#     update-ca-certificates -f;

COPY . /usr/app/HDFS
# RUN echo $JAVA_HOME