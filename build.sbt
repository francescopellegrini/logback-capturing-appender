import Dependencies._

lazy val compileSettings = Seq(
  scalacOptions ++= Seq("-deprecation",
    "-encoding", "utf8",
    "-Xlint:missing-interpolator",
    "-Xlint:private-shadow",
    "-Xlint:type-parameter-shadow",
    "-Ywarn-dead-code",
    "-Ywarn-unused"
  ),
  scalafmtOnCompile := true,
  scalaVersion := Version.Scala
)

lazy val dependenciesSettings = Seq(
  libraryDependencies ++= prodDeps ++ testDeps
)

import ReleaseTransformations._

lazy val publishSettings = Seq(
  Test / publishArtifact := false,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    releaseStepTask(PgpKeys.publishSigned),
    setNextVersion,
    commitNextVersion,
    releaseStepCommand(Sonatype.SonatypeCommand.sonatypeRelease),
    pushChanges
  )
)

lazy val testSettings = Seq(
  Test / parallelExecution := false
)

lazy val root = (project in file("."))
  .settings(
    name := "logback-capturing-appender",
    organization := "com.github.francescopellegrini",
  )
  .settings(compileSettings: _*)
  .settings(dependenciesSettings: _*)
  .settings(publishSettings: _*)
  .settings(testSettings: _*)
