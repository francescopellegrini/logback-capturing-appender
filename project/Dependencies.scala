import sbt._

object Dependencies {

  object Versions {

    val LogbackClassic = "1.2.3"
    val ScalaTest      = "3.0.7"
    val Scala          = "2.12.8"

  }

  lazy val prodDeps: Seq[ModuleID] = Seq(
    "ch.qos.logback" % "logback-classic" % Versions.LogbackClassic
  )

  lazy val testDeps: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % Versions.ScalaTest
  ).map(_ % Test)

}
