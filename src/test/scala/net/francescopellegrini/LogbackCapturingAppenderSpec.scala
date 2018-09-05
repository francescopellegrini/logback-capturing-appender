package net.francescopellegrini

import ch.qos.logback.classic.Level
import org.scalatest.{Assertion, Matchers, OptionValues, WordSpecLike}

object LogbackCapturingAppenderSpec {
  protected class LoggerTestClass {
    import org.slf4j.{Logger, LoggerFactory}

    val logger: Logger = LoggerFactory.getLogger(this.getClass)
  }

  case class Resources(
      clazz: LoggerTestClass
  )
}

class LogbackCapturingAppenderSpec extends WordSpecLike with Matchers with OptionValues {
  import LogbackCapturingAppenderSpec._

  "LogbackCapturingAppender" should {
    "capture a given log event" in withResources { resources =>
      val capturingAppender = LogbackCapturingAppender(resources.clazz.logger)
      val logMessage        = "This message should be logged at debug level"

      resources.clazz.logger.debug(logMessage)

      val event = capturingAppender.loggedEvent.value

      event.getLevel shouldBe Level.DEBUG
      event.getMessage shouldBe logMessage
    }

    "not capture any log event after cleanup" in withResources { resources =>
      val capturingAppender = LogbackCapturingAppender(resources.clazz.logger)
      val logMessage        = "This message should be logged at info level"

      resources.clazz.logger.info(logMessage)

      LogbackCapturingAppender.cleanUp()

      resources.clazz.logger.error("This message should not be logged at all", new Exception)

      val event = capturingAppender.loggedEvent.value

      event.getLevel shouldBe Level.INFO
      event.getMessage shouldBe logMessage
    }

  }

  def withResources(f: Resources => Assertion): Unit = {
    val resources = Resources(
      new LoggerTestClass
    )

    f(resources)

    LogbackCapturingAppender.cleanUp()
  }

}
