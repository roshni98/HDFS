#!/bin/bash
# compile all files
if [[ $1 = '-c' ]]
then
    ##Compile the code
    javac -cp .:protobuf-java-3.11.1.jar  src/ds/hdfs/Client.java
    javac -cp .:protobuf-java-3.11.1.jar  src/ds/hdfs/DataNode.java
    javac -cp .:protobuf-java-3.11.1.jar  src/ds/hdfs/Client.java
    
#run datanode file
elif [[ $1 = '-d' ]]
then
    cd /app/src
    javac -cp .:protobuf-java-3.11.1.jar  ./ds/hdfs/DataNode.java
    java -cp .:protobuf-java-3.11.1.jar ds.hdfs.DataNode

#run the namenode file
elif [[ $1 = '-n' ]]
then
    cd /app/src
    javac -cp .:protobuf-java-3.11.1.jar ./ds/hdfs/NameNode.java
    java -cp .:protobuf-java-3.11.1.jar  ds.hdfs.NameNode
fi