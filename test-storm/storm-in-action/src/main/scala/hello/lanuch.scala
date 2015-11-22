package hello

import backtype.storm.Config
import util.TopologyUtil._

import util.Lanuch

object HelloTopologyLanuch extends Lanuch {

  override def apply(name: String, remote: Boolean): Unit = {
    // 实例化topologyBuilder
    val builder = topologyBuilder()

    // 设置Spout 组件
    val spout_parallelism_hint = 1
    val spoutName = s"${name}_spout"
    builder.setSpout(spoutName, new HelloSpout, spout_parallelism_hint)

    // 设置Bolt 组件
    val say_hello_bolt_parallelism_hint = 2
    val boltSayHello = s"${name}_bolt_sayhello"
    builder.setBolt(boltSayHello, new SayHelloBolt, say_hello_bolt_parallelism_hint)
      .shuffleGrouping(spoutName).setNumTasks(say_hello_bolt_parallelism_hint * 2)

    builder.setBolt(s"${name}_bolt_counthello", new CounterHelloBolt, 2).shuffleGrouping(boltSayHello)

    // 构造topology 实例
    val topology = builder.createTopology()

    // 构造配置对象
    val conf = new Config();
    //conf.setDebug(true);

    submitTopology(name, remote, conf, topology)
  }
}