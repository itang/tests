package accesslogs

import java.util.{ HashMap => JHashMap }
import backtype.storm.topology.base.BaseBasicBolt
import backtype.storm.topology.BasicOutputCollector
import backtype.storm.tuple.Tuple
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.tuple.Values
import backtype.storm.tuple.Fields

class AccessCounterBolt extends BaseBasicBolt {

  override def execute(tuple: Tuple, collector: BasicOutputCollector): Unit = {
    val msg = tuple.getString(0)
    val name = msg.split(" ")(0)
    collector.emit(new Values(name, 1.asInstanceOf[Integer]))
  }

  def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields("name", "couter"))
  }

}

class SumBolt extends BaseBasicBolt {
  type Counters = JHashMap[String, Int]

  val counters = new Counters

  override def execute(tuple: Tuple, collector: BasicOutputCollector): Unit = {
    val name = tuple.getString(0)
    counters.merge(name, 1, (x, y) => x + y)

    println(counters)
  }

  def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields("name", "couter"))
  }
}
