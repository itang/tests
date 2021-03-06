import accesslogs.AccessTopologyLanuch
import hello.HelloAllGroupingTopologyLanuch
import hello.HelloFieldsGroupingTopologyLanuch
import hello.HelloGlobalGroupingTopologyLanuch
import hello.HelloMultiSubscriptionsTopologyLauch
import hello.HelloTopologyLanuch
import util.Args

object Main {

  def main(args: Array[String]): Unit = {
    args match {
      case Args(name @ "hello", remote)           => HelloTopologyLanuch(name, remote)
      case Args(name @ "hello-fields", remote)    => HelloFieldsGroupingTopologyLanuch(name, remote)
      case Args(name @ "hello-global", remote)    => HelloGlobalGroupingTopologyLanuch(name, remote)
      case Args(name @ "hello-all", remote)       => HelloAllGroupingTopologyLanuch(name, remote)
      case Args(name @ "hello-multi-sub", remote) => HelloMultiSubscriptionsTopologyLauch(name, remote)
      case Args(name @ "accesslogs", remote)      => AccessTopologyLanuch(name, remote)

      case _                                      => println("输入参数有误: java -jar xx.jar name [remote] 或者 不支持")
    }
  }
}
