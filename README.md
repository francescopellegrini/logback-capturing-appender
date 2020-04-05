# Logback Capturing Appender

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.francescopellegrini/logback-capturing-appender_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.francescopellegrini/logback-capturing-appender_2.12)
![Build](https://github.com/francescopellegrini/logback-capturing-appender/workflows/Build/badge.svg)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6d82355746e449318afce09ad411e600)](https://app.codacy.com/app/francescopellegrini/logback-capturing-appender?utm_source=github.com&utm_medium=referral&utm_content=francescopellegrini/logback-capturing-appender&utm_campaign=Badge_Grade_Dashboard)

Scala porting of [Patrick Kua class](https://github.com/thekua/Sample-Code/tree/master/java/logback-spike) for capturing Logback appenders output.

## Installation

Add to `build.sbt` (replace x.x.x with the appropriate version):

```scala
libraryDependencies += "com.github.francescopellegrini" %% "logback-capturing-appender" % "x.x.x"
```

## How to use

```scala
val capturingAppender = LogbackCapturingAppender(slf4jLogger).startCapturing()

// log some messages

// Get all events logged at the configured level
capturingAppender.loggedEvents
```
