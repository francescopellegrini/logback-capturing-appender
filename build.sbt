import Dependencies._
import ReleaseTransformations._

lazy val compileSettings = Seq(
  Compile / compile := (Compile / compile)
    .dependsOn(
      Compile / scalafmtSbt,
      Compile / scalafmtAll
    )
    .value,
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding",
    "utf8",
    "-Xlint:missing-interpolator",
    "-Xlint:private-shadow",
    "-Xlint:type-parameter-shadow",
    "-Ywarn-dead-code",
    "-Ywarn-unused"
  ),
  scalaVersion := Versions.Scala
)

lazy val dependenciesSettings = Seq(
  libraryDependencies ++= prodDeps ++ testDeps
)

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
  Test / logBuffered := false,
  Test / parallelExecution := false
)

lazy val root = (project in file("."))
  .settings(
    name := "logback-capturing-appender",
    organization := "com.github.francescopellegrini"
  )
  .settings(compileSettings: _*)
  .settings(dependenciesSettings: _*)
  .settings(publishSettings: _*)
  .settings(testSettings: _*)
