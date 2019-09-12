package test0907

import org.apache.spark.sql.{DataFrame, SparkSession}

object test {

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder()
                .appName(this.getClass.getName)
                .master("local[*]")
                .getOrCreate()
        val df: DataFrame = spark.read.csv("dir/data.txt")
        import spark.implicits._
        val dt: DataFrame = df.toDF("Date","Open","High","Low","Close","Volume")
        dt.createOrReplaceTempView("tables")
        import spark.sql
        sql("select t.week week, " +
                "avg(t.close) avg_close " +
                "from " +
                "(select weekofyear(Date) week, Close from tables) t " +
                "group by t.week").show()

    }

}
case class Sock(Date:String,Open:Double,High:Double,Low:Double,Close:Double,Volume:String){
}