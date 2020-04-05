import sbt._

object CompilerPlugins {
  val compilerPlugins = Seq(
    addCompilerPlugin(scalafix.sbt.ScalafixPlugin.autoImport.scalafixSemanticdb)
  )
}
