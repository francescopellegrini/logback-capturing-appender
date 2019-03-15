import sbt._

object Dependencies {
  
  object Version {

    lazy val LogbackClassic = "1.2.3"
    lazy val ScalaTest      = "3.0.6"
    lazy val Scala          = "2.12.8"

  }
  
  lazy val prodDeps: Seq[ModuleID] = Seq(
    "ch.qos.logback" % "logback-classic" % Version.LogbackClassic
  )

  lazy val testDeps: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % Version.ScalaTest
  ).map(_ % Test)

}
