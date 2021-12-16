// using scala 2.13
// using lib org.wvlet.airframe::airframe:21.12.0

import wvlet.airframe._

case class AppConfig2(appName: String)

class App2(val config: AppConfig2) {
  def showConfig(): Unit = println(config)
}

object Main {
  def main(args: Array[String]): Unit = {
    val d = newDesign.bind[AppConfig2].toInstance(AppConfig2("Hello Airframe!"))

    d.build[App2] { (app: App2) => app.showConfig() }
  }
}
