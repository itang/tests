package hello

import java.util.Date
import java.util.{ Map => JMap }
import backtype.storm.spout.SpoutOutputCollector
import backtype.storm.task.TopologyContext
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.base.BaseRichSpout
import backtype.storm.tuple.Fields
import backtype.storm.tuple.Values
import util.Log
import scala.util.hashing.Hashing
import scala.util.hashing.MurmurHash3
import scala.util.Random

class HelloSpout extends BaseRichSpout with Log {

  var outputCollector: SpoutOutputCollector = _

  override def open(config: JMap[_, _], context: TopologyContext, outputCollector: SpoutOutputCollector) {
    info("open")

    this.outputCollector = outputCollector
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer) {
    info("declare")

    declarer.declare(new Fields("word", "time"));
  }

  override def nextTuple() {
    info("nextTuple")

    val word = List("hello", "hi", "yeah")(Random.nextInt(3))
    val date = new Date
    val objectId = word + date.getTime
    Thread.sleep(5000)
    outputCollector.emit(new Values(word, date), objectId)
  }

  override def ack(id: Object) {
    info(s"spout-ack $id")
  }

  override def fail(id: Object) {
    info(s"spout-fail $id")
  }
}
