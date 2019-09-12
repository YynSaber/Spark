package com.yyn.test_1.day13

import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * @title: InputOutputDemo
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/7 14:12
  */
object InputOutputDemo {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
//      .enableHiveSupport()
      .getOrCreate()

//    spark.read.json("dir/people.json").show()
//    spark.read.text("dir/people.json").show()
//    spark.read.textFile("dir/people.json").show()

    val loaddata = spark.read.format("text").load("dir/people.txt")

//    loaddata.show()
//
//    loaddata.write.format("text").save("h://out_1")
//    loaddata.write.format("json").save("h://out_2")

    loaddata.write.mode(SaveMode.Append).text("h://out_3")
//    loaddata.write.mode(SaveMode.Overwrite).json("h://out_3")

    spark.stop()


  }


}
