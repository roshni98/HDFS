FROM adoptopenjdk/openjdk13

USER root

# To solve add-apt-repository : command not found
RUN apt-get update
RUN apt-get -y install software-properties-common

# # Install Java
# RUN apt-get update && \
#     apt-get install -y openjdk-8-jdk && \
#     apt-get install -y ant && \
#     apt-get clean;

# # Fix certificate issues
# RUN apt-get update && \
#     apt-get install ca-certificates-java && \
#     apt-get clean && \
#     update-ca-certificates -f;

# # Setup JAVA_HOME -- useful for docker commandline
# ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
# RUN export JAVA_HOME

# # passwordless ssh
# RUN ssh-keygen -q -N "" -t dsa -f /etc/ssh/ssh_host_dsa_key
# RUN ssh-keygen -q -N "" -t rsa -f /etc/ssh/ssh_host_rsa_key
# RUN ssh-keygen -q -N "" -t rsa -f /root/.ssh/id_rsa
# RUN cp /root/.ssh/id_rsa.pub /root/.ssh/authorized_keys
ADD . /app
RUN echo $JAVA_HOME
