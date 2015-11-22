import hello.HelloTopologyLanuch
import util.Args

object Main {

  def main(args: Array[String]): Unit = {
    args match {
      case Args(name @ "hello", remote) => HelloTopologyLanuch(name, remote)
      case _                            => println("输入参数有误: java -jar xx.jar name [remote] 或者 不支持")
    }
  }
}

