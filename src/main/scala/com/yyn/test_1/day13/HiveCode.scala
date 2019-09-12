package com.yyn.test_1.day13

import org.apache.spark.sql.SparkSession

/**
  * @title: HiveCode
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/7 11:25
  */
object HiveCode {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("HiveCode")
      .config("spark.sql.warehouse.dir", "D://spark-warehouse")
      .master("local[2]")
      .enableHiveSupport()
      .getOrCreate()
    import spark.implicits._
    import spark.sql
     sql("CREATE TABLE IF NOT EXISTS src_1 (key INT, value STRING)")
    sql("LOAD DATA local INPATH 'dir/kv1.txt' INTO TABLE src_1")
    sql("SELECT * FROM src_1").show()
    sql("SELECT COUNT(*) FROM src_1").show()
    val sqlDF = sql("SELECT key, value FROM src_1 WHERE key < 10 ORDER BY key")
//    sqlDF.show()
    //    sqlDF.as("mixing").show()


//    val recordsDF = spark.createDataFrame((1 to 100).map(i => Record(i, s"val_$i")))
//    recordsDF.createOrReplaceTempView("records")
//    sql("SELECT * FROM records r JOIN src_1 s ON r.key = s.key").show()

    spark.stop()
  }

}
case class Record(key: Int, value: String){}