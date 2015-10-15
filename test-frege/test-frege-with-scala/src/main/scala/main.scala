import frege.prelude.PreludeBase
import frege.runtime.Runtime
import example.HelloWorld

object Main {
  def main(args: Array[String]): Unit = {
    val runMain = Runtime.runMain(
      PreludeBase.TST.performUnsafe(
        HelloWorld.hello(PreludeBase._toList(args)).forced()
      )
    )

    ()
  }
}
