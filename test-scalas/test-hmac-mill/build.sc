// build.sc
import mill._
import mill.define.Target
import mill.scalalib._
import mill.util.Loose

object foo extends ScalaModule {
  def scalaVersion = "2.12.4"

  override def ivyDeps: Target[Loose.Agg[Dep]] =  Agg(
    ivy"commons-codec:commons-codec:1.11",
    ivy"commons-lang:commons-lang:2.6"
  )
}
