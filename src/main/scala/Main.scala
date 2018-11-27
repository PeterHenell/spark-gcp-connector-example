import org.apache.spark.sql._


object Main {


  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Peter H pushing to GCP")
      .getOrCreate()

    import com.google.cloud.hadoop.fs.gcs.{GoogleHadoopFS, GoogleHadoopFileSystem}

    spark.sparkContext.hadoopConfiguration.set("google.cloud.auth.service.account.enable", "true")
    spark.sparkContext.hadoopConfiguration.set("fs.gs.impl", classOf[GoogleHadoopFileSystem].getName)
    spark.sparkContext.hadoopConfiguration.set("spark.hadoop.google.cloud.auth.service.account.enable", "true")
    spark.sparkContext.hadoopConfiguration.set("fs.AbstractFileSystem.gs.impl", classOf[GoogleHadoopFS].getName)
    spark.sparkContext.hadoopConfiguration.set("google.cloud.auth.service.account.json.keyfile","playground-key.json")
    spark.sparkContext.hadoopConfiguration.set("fs.gs.project.id", "playground-193310")

    val csvData = spark.read.csv("gs://spark-playground/oracle-db.go")

    csvData.show()

    csvData.write.mode(SaveMode.Overwrite).csv("gs://spark-playground/outputtest.csv")

  }

}