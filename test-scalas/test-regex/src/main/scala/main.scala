/**
  * main.scala
  */

//import scatang._

object Main {

  def main(args: Array[String]): Unit = {
    val rawString =
      """/**
                      * 待支付
                      */
                     WAIT_PAY("待支付", 0),
                     /**
                      * 已支付
                      */
                     PAID("已支付", 1),
                     /**
                      * 已退订
                      */
                     CANCEL("已退订", 2),
                     /**
                      * 已完成
                      */
                     COMPLETED("已完成", 3),
                     /**
                      * 正在提餐
                      */
                     DRAWING("正在提餐", 4),
                     /**
                      * 部分已提餐
                      */
                     PARTIAL_DRAWN("部分已提餐", 5),
                     /**
                      * 已完成
                      */
                     ALL_DRAWN("已完成", 6),
                     /**
                      * 已取消
                      */
                     ABOLISH("已取消", 7);
      """

    val R = """(.+)\("(.+)".+(\d+).+""".r
    rawString.lines.map(_.trim).foreach {
      case R(key, name, value) => println(s"$key $name, $value")
      case _ =>
    }
  }
}




