package demo

object Main extends App {
  println(s"args.size: ${args.length}")

  val name = if(args.length > 0) args(0) else "itang"
  val p = new Person(name)

  def say() {
    val age = p.sayHello()
    println("*" * 20)
    println(s"${Thread.currentThread()}: ${p.name}/$age")
  }

  val ts = Array(new Thread(() => say), new Thread(() => say), new Thread( () => say))
  ts.map { it =>
    it.start
    it
  }.foreach( _.join )
}
