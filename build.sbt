import Dependencies._

lazy val root = (project in file("."))
  .settings(
    libraryDependencies ++= prodDeps ++ testDeps,
    name := "logback-capturing-appender",
    organization := "com.github.francescopellegrini",
    parallelExecution in Test := false,
    publishArtifact in Test := false,
    scalacOptions ++= Seq("-deprecation",
      "-encoding", "utf8"
    ),
    scalaVersion := "2.12.6"
  )

/**
  * sbt-scalafmt plugin
  */
scalafmtOnCompile := true

/**
  * sbt-release plugin
  */
import ReleaseTransformations._

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommand("publishSigned"),
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeRelease"),
  pushChanges
)
