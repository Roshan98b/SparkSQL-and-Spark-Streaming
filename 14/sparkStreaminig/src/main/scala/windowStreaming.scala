import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType

object windowStreaming {
    def main (args: Array[String]) = {

        val path = args(0)
        val spark = SparkSession.builder().appName("Spark Streaming").getOrCreate()
        import spark.implicits._

        spark.conf.set("spark.sql.shuffle.partitions", "5")

        val rainfallSchema = new StructType()
            .add("Creation_Time", "double")
            .add("Station", "string")
            .add("Rainfall", "float")

        val streamData = spark
            .readStream
            .schema(rainfallSchema)
            .json(path)

        val finalData = streamData.selectExpr(
            "*",
            "cast(cast(Creation_Time as double)/1000000000 as timestamp) as event_time"
        )

        val processStream = finalData
            .groupBy(window(col("event_time"), "10 minutes"))
            .agg(avg("Rainfall"), count("Station"))
            .writeStream
            .queryName("processStream")
            .format("console")
            .outputMode("complete")
            .option("truncate", "false")
            .start()

        processStream.awaitTermination() 

    }
}