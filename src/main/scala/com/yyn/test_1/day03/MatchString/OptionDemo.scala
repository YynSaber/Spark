package com.yyn.test_1.day03.MatchString

object OptionDemo {

  def main(args: Array[String]): Unit = {
//    val seq: Option[(String, Int, Boolean)] = Some("hua",18,true)
//    val value:(String,Int,Boolean)=seq.getOrElse(null)
//    println(value)
    val map=Map(("ting",19),("hua",18),("xiu",20))

    val values=map.get("ting")

    values match {
      case Some(x) => println(s"case some,x: $x")
      case None=>println("case none")
    }

  }

}
