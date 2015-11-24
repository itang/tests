package accesslogs

import backtype.storm.Config
import backtype.storm.scheme.StringScheme
import hello.SayHelloBolt
import util.Lanuch
import util.TopologyUtil.submitTopology
import util.TopologyUtil.topologyBuilder

object AccessTopologyLanuch extends Lanuch {
  override def apply(name: String, remote: Boolean): Unit = {
    // 实例化topologyBuilder
    val builder = topologyBuilder()

    // 设置Spout 组件
    val spoutName = s"${name}_spout"
    builder.setSpout(spoutName, new AccessLogSpout("localhost", 2229, "access", new StringScheme), 2)

    // 设置Bolt 组件
    val counter = s"${name}_bolt_counter"
    builder.setBolt(counter, new AccessCounterBolt, 2).setNumTasks(2).shuffleGrouping(spoutName)
    builder.setBolt(s"${name}_bolt_sum", new SumBolt, 1).shuffleGrouping(counter)

    // 构造topology 实例
    val topology = builder.createTopology()
    // 构造配置对象
    val conf = new Config();
    //conf.setDebug(true);

    submitTopology(name, remote, conf, topology)
  }

}
