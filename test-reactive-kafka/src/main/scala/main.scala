/**
 * main.scala
 */

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import com.softwaremill.react.kafka.KafkaMessages.StringKafkaMessage
import kafka.serializer.{StringDecoder, StringEncoder}
import com.softwaremill.react.kafka.{ReactiveKafka, ProducerProperties, ConsumerProperties}

import org.reactivestreams.{Subscription, Subscriber, Publisher}

object Main {

  def main(args: Array[String]): Unit = {
    implicit val actorSystem = ActorSystem("ReactiveKafka")
    implicit val materializer = ActorMaterializer()

    println("HERE...")
    val kafka = new ReactiveKafka
    val consumerProperties = ConsumerProperties(
      brokerList = "localhost:9092",
      zooKeeperHost = "localhost:2181",
      topic = "topic1",
      groupId = "groupName",
      decoder = new StringDecoder()
    ).consumerTimeoutMs(timeInMs = 100)
      .readFromEndOfStream()
      .kafkaOffsetsStorage(dualCommit = true)

    val publisher: Publisher[StringKafkaMessage] = kafka.consume(consumerProperties)
    val subscriber: Subscriber[String] = new Subscriber[String]() {
      var subscription: Subscription = _
      var _done = false

      override def onError(t: Throwable): Unit = {
        println(">>onError")
        if (subscription == null) {
          (new IllegalStateException("Publisher violated the Reactive Streams rule 1.09 signalling onError prior to onSubscribe.")).printStackTrace(System.err)
        } else {
          if (t == null) throw null;
          // Here we are not allowed to call any methods on the `Subscription` or the `Publisher`, as per rule 2.3
          // And anyway, the `Subscription` is considered to be cancelled if this method gets called, as per rule 2.4
        }
      }

      override def onSubscribe(s: Subscription): Unit = {
        println(">>onSubscribe")
        if (s == null) throw null

        if (subscription != null) {
          try {
            s.cancel()
          } catch {
            case t: Throwable => new IllegalStateException(s + " violated the Reactive Streams rule 3.15 by throwing an exception from cancel.", t)
          }
        } else {
          subscription = s
          try {
            s.request(1)
          } catch {
            case t: Throwable => (new IllegalStateException(s + " violated the Reactive Streams rule 3.16 by throwing an exception from request.", t)).printStackTrace(System.err)
          }
        }
      }

      override def onComplete(): Unit = {
        println(">>onComplete")
        if (subscription == null) {
          // Technically this check is not needed, since we are expecting Publishers to conform to the spec
          (new IllegalStateException("Publisher violated the Reactive Streams rule 1.09 signalling onComplete prior to onSubscribe.")).printStackTrace(System.err)
        } else {
          // Here we are not allowed to call any methods on the `Subscription` or the `Publisher`, as per rule 2.3
          // And anyway, the `Subscription` is considered to be cancelled if this method gets called, as per rule 2.4
        }
      }

      private def done() {
        //On this line we could add a guard against `!done`, but since rule 3.7 says that `Subscription.cancel()` is idempotent, we don't need to.
        _done = true; // If we `foreach` throws an exception, let's consider ourselves done (not accepting more elements)
        try {
          subscription.cancel(); // Cancel the subscription
        } catch {
          case t: Throwable =>
            //Subscription.cancel is not allowed to throw an exception, according to rule 3.15
            (new IllegalStateException(subscription + " violated the Reactive Streams rule 3.15 by throwing an exception from cancel.", t)).printStackTrace(System.err)
        }
      }

      override def onNext(element: String): Unit = {
        println(">>onNext")
        if (subscription == null) {
          // Technically this check is not needed, since we are expecting Publishers to conform to the spec
          (new IllegalStateException("Publisher violated the Reactive Streams rule 1.09 signalling onNext prior to onSubscribe.")).printStackTrace(System.err)
        } else {
          // As per rule 2.13, we need to throw a `java.lang.NullPointerException` if the `element` is `null`
          if (element == null) throw null;

          if (!_done) {
            // If we aren't already done
            try {
              if (foreach(element)) {
                try {
                  subscription.request(1); // Our Subscriber is unbuffered and modest, it requests one element at a time
                } catch {
                  case t: Throwable =>
                    // Subscription.request is not allowed to throw according to rule 3.16
                    (new IllegalStateException(subscription + " violated the Reactive Streams rule 3.16 by throwing an exception from request.", t)).printStackTrace(System.err)
                }
              } else {
                done();
              }
            } catch {
              case t: Throwable =>
                done()
                try {
                  onError(t)
                } catch {
                  case t2: Throwable =>
                    //Subscriber.onError is not allowed to throw an exception, according to rule 2.13
                    (new IllegalStateException(this + " violated the Reactive Streams rule 2.13 by throwing an exception from onError.", t2)).printStackTrace(System.err)
                }
            }
          }
        }
      }

      private def foreach(t: String): Boolean = {
        println("message:" + t)
        true
      }
    }

    Source(publisher).map(_.message).to(Sink(subscriber)).run()

    //val consumerWithOffsetSink = kafka.consumeWithOffsetSink(consumerProperties)
    //Source(publisher).map(x => {
    //  println(x.message())
    //  x
    //}).to(consumerWithOffsetSink.offsetCommitSink).run() // stream back for commit.run()
  }
}
