package example

import java.util.Date
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Inject
import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.google.inject.name.Names
import test_guice.BatchScoped
import test_guice.SimpleScope
import com.google.inject.Key

trait Clock {
  def now(): Date
}

trait Player {
  def play(): Unit
}

class Itang extends Player {
  def play(): Unit = {
    println("coding")
  }
}

class ClockImpl extends Clock {
  override def now(): Date = new Date
}

class ClockImpl2 extends ClockImpl {
  println("init ClockImpl2")
}

class ClockImpl3 extends ClockImpl {
  println("init ClockImpl3")
}

class ClockImpl4 extends ClockImpl {
  println("init ClockImpl4")
}

object Example {

  def echo(s: String) = s

  @Inject
  @Named("prototype")
  private var clock3: Clock = _

  @Inject
  @Named("other")
  private var clock4: Clock = _

  @Inject
  @Named("impl2")
  private var clock5: Clock = _

  @Inject
  @Named("impl3")
  private var clock6: Clock = _

  @Inject
  @Named("batchScope")
  var scope: SimpleScope = _

  def main(args: Array[String]): Unit = {

    println("start createInjector")
    val injector = Guice.createInjector(new MyModule())

    println("end createInjector")

    val clock1 = injector.getInstance(classOf[Clock])
    println(clock1.now)

    val clock2 = injector.getInstance(classOf[Clock])
    println(clock2.now)

    //@Singleton
    assert(clock1 == clock2)

    injector.injectMembers(this)

    println(this.clock3)

    // clock2 prototype
    assert(this.clock3 != clock2)

    println(this.clock4)

    // prototype
    assert(this.clock4 != clock3)
    assert(this.clock4 != clock2)

    scope.enter();
    try {
      // explicitly seed some seed objects...
      val itang = new Itang()
      scope.seed(classOf[Player], itang)

      val b1 = new B()
      val b2 = new B()
      val player1 = injector.injectMembers(b1)

      b1.player.play()

      val player2 = injector.injectMembers(b2)
      b1.player.play()

      assert(b1.player == b1.player)
      assert(b1.player == itang)

      val t = new Thread() {
        override def run() {
          scope.enter()
          val b3 = new B()
          val player2 = injector.injectMembers(b3)
          b3.player.play()
          assert(b3.player != b1.player)
          scope.exit()
        }
      }
      t.start()

      t.join()
    } finally {
      scope.exit();
    }

    val pi = injector.getInstance(classOf[ProvidersInject])

    // @Provides 的对象不会再自动injectMembers。 Notice: 跟spring @Bean的区别
    assert(pi.clock == null)

    injector.injectMembers(pi)
    assert(pi.clock != null)
  }

  class B {
    @Inject
    var player: Player = _
  }

  class ProvidersInject {
    @Inject
    var clock: Clock = _
  }

  class MyModule extends AbstractModule {

    override def configure() {
      bind(classOf[Clock]).to(classOf[ClockImpl]).in(classOf[Singleton])
      bind(classOf[Clock]).annotatedWith(Names.named("prototype")).to(classOf[ClockImpl])
      bind(classOf[Clock]).annotatedWith(Names.named("impl2")).to(classOf[ClockImpl2]).in(classOf[Singleton])
      bind(classOf[Clock]).annotatedWith(Names.named("impl3")).to(classOf[ClockImpl3]).asEagerSingleton()

      val batchScope = new SimpleScope()
      bindScope(classOf[BatchScoped], batchScope);

      bind(classOf[SimpleScope])
        .annotatedWith(Names.named("batchScope"))
        .toInstance(batchScope);
      bind(classOf[Player]).to(classOf[Itang]).in(classOf[BatchScoped])
    }

    @Provides
    @Named("other")
    def clock(): Clock = new ClockImpl()

    @Provides
    def pi(): ProvidersInject = new ProvidersInject()
  }
}

