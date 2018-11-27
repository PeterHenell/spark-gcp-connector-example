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

Tested on Cloudera (Hadoop 2)

Note that the playground-key.json must be in the current directory

```
spark2-submit  --deploy-mode client --num-executors 5  --jars /etc/hbase/conf/hbase-site.xml,playground-key.json ~/hello-world-assembly-1.0.jar
```


# Tricks to get this working

The dependency of ```"com.google.cloud.bigdataoss" % "gcs-connector" % "hadoop2-1.9.10"``` does in turn have dependencies on Guava, which is of incompatible version of the one used in hadoop2.

We use ShadeRule to relocate that dependency because the one used in Hadoop will always overwrite our version. 
See (https://cloud.google.com/blog/products/data-analytics/managing-java-dependencies-apache-spark-applications-cloud-dataproc) for a better clarification of the issue.

To compile, we also need the hadoop common library from cloudera.
