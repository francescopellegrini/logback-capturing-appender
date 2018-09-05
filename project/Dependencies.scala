import sbt._

object Dependencies {
  lazy val logbackClassicVersion = "1.2.3"
  lazy val scalaTestVersion      = "3.0.5"

  lazy val prodDeps: Seq[ModuleID] = Seq(
    "ch.qos.logback" % "logback-classic" % logbackClassicVersion
  )

  lazy val testDeps: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion
  ).map(_ % Test)

}
