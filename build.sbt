import Dependencies._
import ReleaseTransformations._
import sbtrelease.Version

addCommandAlias("fix", "all compile:scalafix test:scalafix")
addCommandAlias("fixCheck", "; compile:scalafix --check ; test:scalafix --check")

lazy val compileSettings = Seq(
  Compile / compile := (Compile / compile)
    .dependsOn(
      Compile / scalafmtSbt,
      Compile / scalafmtAll
    )
    .value,
  scalaVersion := Versions.Scala
) ++ CompilerPlugins.compilerPlugins

lazy val dependenciesSettings = Seq(
  libraryDependencies ++= prodDeps ++ testDeps,
  ThisBuild / scalafixDependencies ++= scalafixDeps
)

lazy val publishSettings = Seq(
  licenses += ("MIT", url("https://opensource.org/licenses/MIT")),
  Test / publishArtifact := false,
  developers := List(
    Developer(
      "francescopellegrini",
      "Francesco Pellegrini",
      "francesco.pelle@gmail.com",
      url("https://github.com/francescopellegrini")
    )
  )
)

lazy val releaseSettings = Seq(
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    setNextVersion,
    commitNextVersion,
    pushChanges
  ),
  releaseVersionBump := Version.Bump.Minor
)

lazy val testSettings = Seq(
  Test / logBuffered := false,
  Test / parallelExecution := false
)

lazy val root = (project in file("."))
  .settings(
    name := "logback-capturing-appender",
    organization := "com.github.francescopellegrini",
    homepage := Some(url("https://github.com/francescopellegrini/logback-capturing-appender"))
  )
  .settings(compileSettings: _*)
  .settings(dependenciesSettings: _*)
  .settings(publishSettings: _*)
  .settings(releaseSettings: _*)
  .settings(testSettings: _*)
