import xerial.sbt.Sonatype._

publishTo := sonatypePublishTo.value

sonatypeProjectHosting := Some(
  GitHubHosting(
    user = "francescopellegrini",
    repository = name.value,
    fullName = "Francesco Pellegrini",
    email = "francesco.pelle@gmail.com"
  )
)

licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))
