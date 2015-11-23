package hello

import backtype.storm.Config
import util.TopologyUtil._
import util.Lanuch
import backtype.storm.tuple.Fields
import backtype.storm.topology.BoltDeclarer

case class Parallelism(parallelism_hint: Int = 1, num_tasks: Int = 1)

case class LauchConf(spout: Parallelism, sayBolt: Parallelism, counterBolt: Parallelism)

abstract class BaseTopologyLauch(lauchConf: LauchConf, grouping: (String, BoltDeclarer) => BoltDeclarer) extends Lanuch {

  override def apply(name: String, remote: Boolean): Unit = {
    // 实例化topologyBuilder
    val builder = topologyBuilder()

    // 设置Spout 组件
    val spout_parallelism_hint = lauchConf.spout.parallelism_hint;
    val spoutName = s"${name}_spout"
    builder.setSpout(spoutName, new HelloSpout, spout_parallelism_hint)

    // 设置Bolt 组件
    val say_hello_bolt_parallelism_hint = lauchConf.sayBolt.parallelism_hint
    val boltSayHello = s"${name}_bolt_sayhello"
    builder.setBolt(boltSayHello, new SayHelloBolt, say_hello_bolt_parallelism_hint)
      .shuffleGrouping(spoutName).setNumTasks(lauchConf.sayBolt.num_tasks)

    grouping(spoutName, builder.setBolt(s"${name}_bolt_counthello", new CounterHelloBolt, lauchConf.counterBolt.parallelism_hint))

    // 构造topology 实例
    val topology = builder.createTopology()

    // 构造配置对象
    val conf = new Config();
    //conf.setDebug(true);

    submitTopology(name, remote, conf, topology)
  }
}

object HelloTopologyLanuch extends BaseTopologyLauch(
  LauchConf(Parallelism(1), Parallelism(2, 4), Parallelism(2, 2)),
  (name, dc) => dc.shuffleGrouping(name))

object HelloFieldsGroupingTopologyLanuch extends BaseTopologyLauch(
  LauchConf(Parallelism(1), Parallelism(2, 4), Parallelism(1, 1)),
  (name, dc) => dc.globalGrouping(name))

object HelloGlobalGroupingTopologyLanuch extends BaseTopologyLauch(
  LauchConf(Parallelism(1), Parallelism(2, 4), Parallelism(1, 1)),
  (name, dc) => dc.globalGrouping(name))

object HelloAllGroupingTopologyLanuch extends BaseTopologyLauch(
  LauchConf(Parallelism(1), Parallelism(2, 4), Parallelism(1, 1)),
  (name, dc) => dc.allGrouping(name))

