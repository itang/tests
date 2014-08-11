package example

import org.apache.zookeeper.ZooKeeper
import org.apache.zookeeper.Watcher
import org.apache.zookeeper.WatchedEvent
import org.apache.zookeeper.ZooDefs.Ids
import org.apache.zookeeper.CreateMode
import collection.JavaConversions._
import util.Try

object ClientBase {
  val CONNECTION_TIMEOU = 2000
}

object Example {

  def echo(s: String) = s

  implicit class ZooKeeperWrapper(val zk: ZooKeeper) {
    def hasChildren(path: String) = !zk.getChildren(path, false).isEmpty()

    def recDelete(path: String) {
      if (hasChildren(path)) {
        for (c <- zk.getChildren(path, false)) {
          recDelete(path + "/" + c)
        }
      }
      Try(zk.delete(path, -1))
    }
  }

  def main(args: Array[String]): Unit = {

    testZk()
  }

  def testZk(): Unit = {
    withZk(new ZooKeeper("localhost:2181", ClientBase.CONNECTION_TIMEOU, new Watcher() {
      override def process(event: WatchedEvent): Unit = {
        println("已经触发了" + event.toString() + "事件！")
      }
    })) { implicit zk: ZooKeeper =>

      cleanup

      // 创建一个目录节点
      zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)

      // 创建一个子目录节点
      zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
      println("get /testRootPath:" + new String(zk.getData("/testRootPath", true, null)))

      // 取出子目录节点列表
      println("get_children /testRootPath: " + zk.getChildren("/testRootPath", true))

      // 修改子目录节点数据
      zk.setData("/testRootPath/testChildPathOne", "modifyChildDataOne".getBytes(), -1)
      println("目录节点状态：[" + zk.exists("/testRootPath", true) + "]")

      // 创建另外一个子目录节点
      zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
      println(new String(zk.getData("/testRootPath/testChildPathTwo", true, null)))

      zk.create("/testRootPath/testChildPathOne/test", "testChildDataOnetest".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)

      zk.create("/testWorks", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
      zk.create("/testWorks/worker", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL)
      zk.create("/testWorks/worker", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL)
      zk.create("/testWorks/worker", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL)

      traverseZNode("/testRootPath")

      traverseZNode("/testWorks")

      cleanup

      //再创建一个零时的
      Option(zk.exists("/test1", false)) match {
        case None => zk.create("/test1", "test1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
        case _ =>
      }
      //再创建一个零时的
      zk.create("/test2", "test2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL)
    }
  }

  def traverseZNode(path: String)(implicit zk: ZooKeeper) {
    def t(path: String, level: Int) {
      println(spaces((if (zk.hasChildren(path)) "+" else "-"), level) + path)
      for (c <- zk.getChildren(path, false)) {
        t(path + "/" + c, level + 1)
      }
    }
    t(path, 0)
  }

  def spaces(c: String, level: Int): String = c * level * 4

  def withZk(zk: ZooKeeper)(implicit f: ZooKeeper => Unit) {
    f(zk)
    // 关闭连接
    zk.close()
  }

  def cleanup(implicit zk: ZooKeeper) {
    Try(zk.recDelete("/testRootPath"))
    Try(zk.recDelete("/testWorks"))
  }
}

