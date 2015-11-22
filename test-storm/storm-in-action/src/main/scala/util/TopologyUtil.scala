package util

import backtype.storm.StormSubmitter
import backtype.storm.LocalCluster
import backtype.storm.generated.StormTopology
import backtype.storm.topology.TopologyBuilder
import backtype.storm.Config

object TopologyUtil {

  def submitTopology(name: String, remote: Boolean, conf: Config, topology: StormTopology): Unit = {
    if (remote) {
      // 设置workers数为3.
      conf.setNumWorkers(3);
      //将topology ([tə'pɒlədʒɪ])提交到Storm集群.
      StormSubmitter.submitTopologyWithProgressBar(name, conf, topology)
    } else {
      println("**本地集群运行...")
      // 构造本地集群(多线程模拟环境).
      val cluster = new LocalCluster
      //将topology ([tə'pɒlədʒɪ])提交到本地集群.
      cluster.submitTopology(name, conf, topology)
      //      // 本地集群下运行10秒.
      //      Thread.sleep(10000)
      //      // 将此topology kill掉.
      //      cluster.killTopology(name)
      //      // 关闭本地集群.
      //      cluster.shutdown()
    }
  }

  def topologyBuilder() = new TopologyBuilder

}
