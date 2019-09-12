package com.yyn.test_1.day13

import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * @title: HiveCode2
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/7 11:40
  */
object HiveCode2 {

  def main(args: Array[String]): Unit = {
    /*val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
//      .config("spark.warehource.dir","hdfs://hadoop01:8020/sparksql_warehource")
      .enableHiveSupport()
      .getOrCreate()

    spark.sql("create table if not exists src_8(key int,value string)")
    spark.sql("load data local inpath 'dir/kv1.txt' into table src_8")

    val df = spark.sql("select * from src_8 where key <= 10 group by key")
    df.show()

    //df.write.mode(SaveMode.Append).csv("hdfs://hadoop01:8020/out_0807_2")*/
    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .config("spark.sql.warehouse.dir", "D://spark-warehouse")
      .master("local[2]")
      .enableHiveSupport()
      .getOrCreate()
    import spark.implicits._
    import spark.sql
    sql("CREATE TABLE IF NOT EXISTS src_2 (key INT, value STRING)")
    sql("LOAD DATA INPATH 'hdfs://hadoop01:8020/spark/kv1.txt' INTO TABLE src_2")
    sql("SELECT * FROM src_2").show()
    sql("SELECT COUNT(*) FROM src_2").show()
    val sqlDF = sql("SELECT key, value FROM src_2 WHERE key < 10 ORDER BY key")
//    sqlDF.write.mode(SaveMode.Append).csv("hdfs://hadoop01:8020/spark_1")



    spark.stop()

  }

}
