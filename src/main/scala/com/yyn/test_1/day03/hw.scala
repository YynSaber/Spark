package com.yyn.test_1.day03

object hw {
  def main(args: Array[String]): Unit = {
//    println(getNum(Array(1, 2, 3, 2, 4, 1, 5), 2))
//    println(getNum1(Array(1, 2, 3, 2, 4, 1, 5), 2).toBuffer)

//    change(Array(1,2,3,4,5,6))

    println(swap(Array(1, 2, 3, 4, 5, 6)).toBuffer)

  }

  def getNum(arr:Array[Int],n:Int): (Int,Int,Int)={
    var a,b,c=0
    for(i <- arr){
      if(i>n){
        a+=1
      }
      else if(i==n){
        b+=1
      }
      else{
        c+=1
      }
    }
    (a,b,c)
  }

  def getNum1(arr:Array[Int],n:Int):Array[Int]={
    Array(arr.count(x=>x>n),arr.count(x=>x==n),arr.count(x=>x<n))
  }

  def change(arr:Array[Int])={
    var p =0
    var q =1

    for(i <- 0 until arr.length){
      if(p<arr.length-1) {
        var temp = arr(p)
        arr(p) = arr(q)
        arr(q) = temp

        p+=2
        q=p+1
      }
    }


    println(arr.toBuffer)
  }

  def swap(arr: Array[Int]): Array[Int] = {
    for (i <- 0 until(arr.length - 1, 2)) {
      println(arr(i))
      //定义一个中间变量
      val tmp = arr(i)
      arr(i) = arr(i + 1)
      arr(i + 1) = tmp
    }
    arr
  }

}

