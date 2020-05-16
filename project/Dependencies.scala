import sbt._

object Dependencies {

  object Versions {

    val LogbackClassic      = "1.2.3"
    val ScalaTest           = "3.1.2"
    val Scala               = "2.12.11"
    val ScalafixSortImports = "0.3.2"

  }

  lazy val scalafixDeps: Seq[ModuleID] = Seq(
    "com.nequissimus" %% "sort-imports" % Versions.ScalafixSortImports
  )

  lazy val prodDeps: Seq[ModuleID] = Seq(
    "ch.qos.logback" % "logback-classic" % Versions.LogbackClassic
  )

  lazy val testDeps: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % Versions.ScalaTest
  ).map(_ % Test)

}
