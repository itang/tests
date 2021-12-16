// using scala 3.1.0
// using lib org.wvlet.airframe::airframe:21.12.0

import wvlet.airframe._

case class AppConfig(appName: String)

class App(val config: AppConfig):
  def showConfig(): Unit = println(config)

@main
def main(): Unit =
  val d = newDesign.bind[AppConfig].toInstance(AppConfig("Hello Airframe!"))

  d.build[App] { (app: App) => app.showConfig() }
