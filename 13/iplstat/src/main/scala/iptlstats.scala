import org.apache.spark.sql.SparkSession

object IPLStats {
    def main(args: Array[String]) = {
        val path = args(0)
        val spark = SparkSession.builder().appName("IPL Stats").getOrCreate()

        import spark.implicits._

        val matchDF = spark.read.format("csv")
                    .option("sep", ",")
                    .option("inferScheme", "true")
                    .option("header", "true")
                    .load(path)

        matchDF.createOrReplaceTempView("match")

        val N = spark.sql("select count(*) from match").first()(0).asInstanceOf[Long]

        val statsDF = spark.sql("select * from match where Match_Winner_Id = Toss_Winner_Id")

        statsDF.createOrReplaceTempView("stats")

        val M = spark.sql("select count(*) from stats").first()(0).asInstanceOf[Long]

        println("Percentage = " + (M*100)/N)

    }
}