package accesslogs

import backtype.storm.spout.KestrelThriftSpout
import backtype.storm.spout.Scheme

class AccessLogSpout(host: String, port: Int, queneName: String, scheme: Scheme)
  extends KestrelThriftSpout(host, port, queneName, scheme)
