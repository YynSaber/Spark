package com.yyn.test_1.day12

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

/**
  * @title: SqlDemo
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/6 15:43
  */
object SqlDemo {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

//    val spark1 = SparkSession.builder()
//      .appName(this.getClass.getName)
//      .master("local[2]")
//      .getOrCreate()

    val df: DataFrame = spark.read.json("dir/people.json")

    //注册一张临时表
    df.createOrReplaceTempView("t_people")

    //注册一张全局临时表 在该应用程序中都可以访问
    //访问的时候需要全路径访问
    //临时表是 Session 范围内的， Session 退出后，表就失效了。如果想应用范围内有效，可以
    //使用全局表。注意使用全局表时需要全路径访问， 如： global_temp.people
    df.createGlobalTempView("t_global_people")

    //开始sql
    val res: DataFrame = spark.sql("select * from t_people")

    //访问grouble表
//    spark.sql("select * from global_temp.t_global_people").show()
    res.as("asas").show()
//    spark.sql("select * from asas").show()
//    spark.stop()

    val spark1 = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val ss: SparkSession = spark.newSession()

    println("spark"+spark)
    println("spark1"+spark1)

//    spark.newSession().sql("select * from global_temp.t_global_people where age >20").show()
//    spark1.sql("select * from t_people where age >20").show()

//    spark1.sql("select * from global_temp.t_global_people where age >20").show()

//    spark1.sql("select * from t_people where age <20").show()


//    res.write.mode(SaveMode.Append).save("hdfs://hadoop01:8020/out_0806_1")
//    res.write.mode(SaveMode.Append).csv("hdfs://hadoop01:8020/out_0806_2")




  }

}
