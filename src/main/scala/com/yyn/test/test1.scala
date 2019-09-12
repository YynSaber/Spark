package com.yyn.test

import java.util.Properties

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Dataset, SaveMode, SparkSession}

/**
  * @title: test1
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/8 14:12
  */
object test1 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[2]")
    val sc = new SparkContext(conf)
    val spark = SparkSession.builder()
      .config(conf)
      .getOrCreate()

    val datas: Dataset[String] = spark.read.textFile("dir/test.txt")

    import spark.implicits._
    val words: Dataset[String] = datas.flatMap(_.split(" "))

    words.createOrReplaceTempView("t_word")

    val res: DataFrame = spark.sql("select value as name,count(*) as salary from t_word group by value order by salary desc")

    val res1: DataFrame = spark.sql("select value as word,count(*) as num from t_word group by word")

    //    val res: DataFrame = spark.sql("select count(*) sum from t_word")

    res1.show()

//    val employee = spark.read.json("dir/employee.json")
//    val nameAndSalaryDF = employee.select("name","salary")

//    val prop = new Properties()
//    prop.put("user","root")
//    prop.put("password","123456")
//
//    val url = "jdbc:mysql://localhost:3306/test"
//
//    res.write.mode(SaveMode.Append).jdbc(url,"employee",prop)



    spark.stop()
  }

}
