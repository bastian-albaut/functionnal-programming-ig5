import org.apache.spark.sql.{SaveMode, SparkSession}

object SparkApp {
    def main(args: Array[String]): Unit = {
        // Replace with the actual connection URI and credentials
        val url = "neo4j://localhost:7687"
        val username = "neo4j"
        val password = "7qK81wOoCQPl0oFNaBye-oqv__SmTv_PNcHh0Kkt8U8"
        val dbname = "testtesttest"

        val spark = SparkSession.builder
            .config("neo4j.url", url)
            .config("neo4j.authentication.basic.username", username)
            .config("neo4j.authentication.basic.password", password)
            .config("neo4j.database", dbname)
            .appName("Spark App")
            .master("local[*]")
            .getOrCreate()

        val data = spark.read.json("example.jsonl")

        // Write to Neo4j
        data.write
            .format("org.neo4j.spark.DataSource")
            .mode(SaveMode.Overwrite)
            .option("labels", "Person")
            .option("node.keys", "name,surname")
            .save()

        // Read from Neo4j
        val ds = spark.read
            .format("org.neo4j.spark.DataSource")
            .option("labels", "Person")
            .load()

        ds.show()
    }
}