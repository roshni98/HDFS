#!/bin/bash
# compile all files
if [ $1 = '-c' ]
then
    ##Compile the code
    javac src/ds/hdfs/Client.java
    javac src/ds/hdfs/DataNode.java
    javac src/ds/hdfs/Client.java
    
#run datanode file
elif [ $1 = '-d' ]
then
    (cd src && java ds.hdfs.DataNode)
    java ds.hdfs.DataNode

#run the namenode file
elif [ $1 = '-n' ]
then
    (cd src && java ds.hdfs.NameNode)
    java ds.hdfs.NameNode

#run the client file
elif [ $1 = '-i' ]
then
    (cd src && java ds.hdfs.Client)
    java ds.hdfs.Client
fi