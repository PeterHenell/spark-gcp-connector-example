import sbt.Keys.name

lazy val commonSettings = Seq(
  scalaVersion := "2.11.0",
  name := "hello-world",
  organization := "com.atlas",
  version := "1.0"
)


resolvers ++= Seq(
  "cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos"
)

lazy val shaded = (project in file("."))
  .settings(commonSettings)

mainClass in (Compile, packageBin) := Some("Main")


libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-sql_2.11" % "2.1.0" % "provided",
  "org.apache.spark" % "spark-core_2.11" % "2.1.0" % "provided",
  "org.apache.hadoop" % "hadoop-common" % "2.6.0-cdh5.12.1" % "provided",
//  "org.apache.hadoop" % "hadoop-core" % "2.6.0-mr1-cdh5.12.1" % "provided",
  ("com.google.cloud.bigdataoss" % "gcs-connector" % "hadoop2-1.9.10").
    exclude("javax.jms", "jms").
    exclude("javax.jms", "jmx").
    exclude("com.sun.jmx", "jmxri").
    exclude("com.sun.jdmk", "jmxtools")
)

assemblyShadeRules in assembly := Seq(
  ShadeRule.rename("com.google.common.**" -> "repackaged.com.google.common.@1").inAll
)
