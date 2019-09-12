package com.yyn.test_1.day01

object test_01 {
  def main(args:Array[String]):Unit = {
//    println("hello scala")
//    val str="hello world";
//    println(str);

    //字符串引用变量值
//    val name = "yiyi"
//    val age = 18
//
//    val user = s"name: ${name} age: ${age}"
//    println(user)

//    val prop="key1=a,key2=b,key3=c"
//    val str =
//      """
//        |key1=a
//        |key2=b
//        |key3=c
//      """.stripMargin
//    Unit
//    println(str)

//    m1()if i!=j)
    ////      println((10*i+j)+"")
//    for(i <- 1 to 3;j<- 1 to 3
//    println()

//    import util.control.Breaks
//    var flag = new Breaks
//    flag.breakable(
//      for(i<-0 until 10){
//        if(i==5){
//          flag.break()
//        }
//        println(i)
//      }
//    )
//
//    for(i<-0 until 10){
//        breakable{
//        if(i==5){
//          break
//        }
//        println(i)
//      }
//    }

    println(m2(3))


  }

  // 模拟门店(游戏厅)的门禁
  def m1(): Unit = {
//    val namme = readLine()
    val name = scala.io.StdIn.readLine("Welcome to XX: \n")
    print("Thanks,Then please tell me your age :\n")
    val age = scala.io.StdIn.readInt()

    if( age>18){
      println(s"hi,you are $age year old,so you are legal to come here")
    }else{
      println(s"sorry,boy,$name,you are only $age")
    }

  }

  def m2(n: Int):Int={
    if(n==1 || n==2) 1
    else m2(n-1)+m2(n-2)
//    val num=m2(n-1)+m2(n-2)
  }

}
