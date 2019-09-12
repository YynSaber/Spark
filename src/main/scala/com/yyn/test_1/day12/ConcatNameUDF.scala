package com.yyn.test_1.day12

import org.apache.spark.sql.api.java.UDF1

/**
  * @title: ConcatNameUDF
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/6 16:33
  */
class ConcatNameUDF extends UDF1[String,String]{
  override def call(t1: String): String = {
    "name:" + t1
  }
}
