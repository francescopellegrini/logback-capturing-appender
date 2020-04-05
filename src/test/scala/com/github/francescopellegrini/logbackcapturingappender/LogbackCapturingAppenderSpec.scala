package com.github.francescopellegrini.logbackcapturingappender

import ch.qos.logback.classic.Level
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpecLike
import org.scalatest.{OptionValues, Outcome}

class LogbackCapturingAppenderSpec extends FixtureAnyWordSpecLike with Matchers with OptionValues {

  "LogbackCapturingAppender" should {
    "capture log events" in { f =>
      val logMessages = Seq("This message should be logged at debug level", "Also this message should be logged at debug level")

      logMessages.foreach(f.testClass.logger.debug)

      f.capturingAppender.loggedEvents.collect {
        case event if event.getLevel == Level.DEBUG => event.getMessage
      }.toList should contain theSameElementsInOrderAs logMessages
    }

    "not capture any log event after cleanup" in { f =>
      val logMessages = Seq("This message should be logged at info level", "Also this message should be logged at info level")

      logMessages.foreach(f.testClass.logger.info)

      f.capturingAppender.cleanup()

      f.testClass.logger.error("This message should not be logged at all", new Exception)

      f.capturingAppender.loggedEvents.collect {
        case event if event.getLevel == Level.INFO => event.getMessage
      }.toList should contain theSameElementsInOrderAs logMessages
    }

  }

  // Override FixtureParam type definition
  case class FixtureParam(testClass: LoggerTestClass, capturingAppender: LogbackCapturingAppender)

  override def withFixture(test: OneArgTest): Outcome = {
    // Build the fixture
    val testClass         = new LoggerTestClass
    val capturingAppender = LogbackCapturingAppender(testClass.logger).startCapturing()
    val theFixture        = FixtureParam(testClass, capturingAppender)

    withFixture(test.toNoArgTest(theFixture)) // "loan" the fixture to the test
  }

}
