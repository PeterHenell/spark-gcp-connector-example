# spark-gcp-connector-example
Example of reading and writing files in gcp using spark on-premise

# Prerequisites

  * Create a bucket in GCP.
  * Create a service account in your GCP account and grant it permissions to read and write to the bucket.
  * Generate a json key file for the service account, download it, and rename it to playground-key.json (can have any name, this name is used by this demo code)
  * Store the file in the same directory as the source root of this project.

## Run on local spark

Tested using Spark 2.11 on Mac

Note that the playground-key.json must be in the current directory
```
sbt assembly && spark-submit target/scala-2.11/hello-world-assembly-1.0.jar
```

## Run on Hadoop

Note that the playground-key.json must be in the current directory

```
spark2-submit  --deploy-mode client --num-executors 5  --jars /etc/hbase/conf/hbase-site.xml,playground-key.json ~/hello-world-assembly-1.0.jar
```
