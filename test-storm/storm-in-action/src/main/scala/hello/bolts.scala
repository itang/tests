package hello

import java.util.Date
import java.util.{ HashMap => JHashMap }
import java.util.{ Map => JMap }

import backtype.storm.task.OutputCollector
import backtype.storm.task.TopologyContext
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.base.BaseRichBolt
import backtype.storm.tuple.Fields
import backtype.storm.tuple.Tuple
import backtype.storm.tuple.Values
import util.Log

class SayHelloBolt extends BaseRichBolt with Log {

  var collector: OutputCollector = _

  def prepare(conf: JMap[_, _], context: TopologyContext, collector: OutputCollector): Unit = {
    info("prepare")

    this.collector = collector
  }

  def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    info("declareOutputFields")
    declarer.declare(new Fields("word", "time"))
  }

  def execute(tuple: Tuple): Unit = {
    val word = tuple.getString(0)
    val _time = tuple.getValueByField("time")

    info(s"execute: ${word} ${formatDate(_time.asInstanceOf[Date])}")

    this.collector.emit(tuple, new Values(word.toUpperCase(), new Date))
    this.collector.ack(tuple);
  }
}

class CounterHelloBolt extends BaseRichBolt with Log {

  var collector: OutputCollector = _

  type Counters = JHashMap[String, Integer]
  val counters = new Counters

  def prepare(conf: JMap[_, _], context: TopologyContext, collector: OutputCollector): Unit = {
    info("prepare")

    this.collector = collector
  }

  def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    info("declareOutputFields")

    declarer.declare(new Fields("word", "counter"))
  }

  def execute(tuple: Tuple): Unit = {
    info(s"execute: ${counters}")

    val word = tuple.getString(0)
    val counter = counters.merge(word, 1, (o, n) => o + n)

    val _time = tuple.getValueByField("time")

    // 没有后续bolt消费， 此bolt emit 的tuple不纳入整个tuple tree? 不影响ack?
    collector.emit(tuple, new Values(word, counter))

    collector.ack(tuple)
  }
}
