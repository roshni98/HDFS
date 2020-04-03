JFLAGS_COMPILE = -d bin -sourcepath src -cp .:src/com/google/protobuf/protobuf-java-3.11.1.jar
JFLAGS_RUN = -cp ./bin:src/com/google/protobuf/protobuf-java-3.11.1.jar
JC = javac
J = java

.SUFFIXES: .java .class

compile:
	$(JC) $(JFLAGS_COMPILE) src/ds/hdfs/INameNode.java src/ds/hdfs/IDataNode.java src/ds/hdfs/NameNode.java src/ds/hdfs/DataNode.java src/ds/hdfs/Client.java

nn:
	$(J) $(JFLAGS_RUN) ds.hdfs.NameNode

dn:
	$(J) $(JFLAGS_RUN) ds.hdfs.DataNode

client:
	$(J) $(JFLAGS_RUN) ds.hdfs.Client

clean:
	rm -r ./bin/*
