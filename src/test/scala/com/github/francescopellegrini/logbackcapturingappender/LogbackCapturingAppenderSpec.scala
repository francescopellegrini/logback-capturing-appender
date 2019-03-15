package com.github.francescopellegrini.logbackcapturingappender

import ch.qos.logback.classic.Level
import org.scalatest.{Matchers, OptionValues, Outcome, fixture}

class LogbackCapturingAppenderSpec extends fixture.WordSpecLike with Matchers with OptionValues {

  "LogbackCapturingAppender" should {
    "capture a given log event" in { f =>
      val capturingAppender = LogbackCapturingAppender(f.clazz.logger)
      val logMessage        = "This message should be logged at debug level"

      f.clazz.logger.debug(logMessage)

      val event = capturingAppender.loggedEvent.value

      event.getLevel shouldBe Level.DEBUG
      event.getMessage shouldBe logMessage
    }

    "not capture any log event after cleanup" in { f =>
      val capturingAppender = LogbackCapturingAppender(f.clazz.logger)
      val logMessage        = "This message should be logged at info level"

      f.clazz.logger.info(logMessage)

      LogbackCapturingAppender.cleanUp()

      f.clazz.logger.error("This message should not be logged at all", new Exception)

      val event = capturingAppender.loggedEvent.value

      event.getLevel shouldBe Level.INFO
      event.getMessage shouldBe logMessage
    }

  }

  // Override FixtureParam type definition
  case class FixtureParam(clazz: LoggerTestClass)

  override def withFixture(test: OneArgTest): Outcome = {
    // Build the fixture
    val theFixture = FixtureParam(new LoggerTestClass)

    try {
      withFixture(test.toNoArgTest(theFixture)) // "loan" the fixture to the test
    } finally {
      LogbackCapturingAppender.cleanUp()
    }
  }

}
