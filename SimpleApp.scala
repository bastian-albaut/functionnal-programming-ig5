import org.apache.spark.sql.SparkSession

object SimpleApp {
  def main(args: Array[String]): Unit = {
    val logFile = "data/testFile.md"
    
    // Specify the master as local
    val spark = SparkSession.builder
      .appName("Simple Application")
      .master("local[*]")
      .getOrCreate()
    
    val logData = spark.read.textFile(logFile).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")
    
    spark.stop()
  }
}
