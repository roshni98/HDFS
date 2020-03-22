#!/usr/bin/env bash
service sshd restart
echo 'Y' | hdfs namenode -format
/usr/local/hadoop/sbin/start-dfs.sh
jps
echo RUNNING!
sleep infinity
