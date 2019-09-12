package com.yyn.test_1.day12

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.IntType
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: DataFrameDemo3
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/6 14:19
  */
object DataFrameDemo3 {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[2]")
    val sc = new SparkContext(conf)
    val spark = SparkSession.builder().config(conf).getOrCreate()

    //获取数据并切分 生成Row类型数据
    val peopleRowRdd: RDD[Row] = sc.textFile("dir/people.txt").map(x => {
      val fields = x.split(",")
      Row(fields(0), fields(1).toInt)
    })

    //通过StructType生成Schema
    val schema: StructType = StructType(
      Array(
        StructField("name", StringType, true),
        StructField("age", IntegerType, true)
      )
    )
    println(schema)

    //转换生成DataFrame
    val df: DataFrame = {
      spark.createDataFrame(peopleRowRdd, schema)
    }

//    df.registerTempTable("t_people")
//    val tab: DataFrame = spark.sql("select * from t_people where age >20")
//    tab.show()
    df.show()

    sc.stop()
    spark.stop()

  }

}
