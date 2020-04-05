package com.github.francescopellegrini.logbackcapturingappender

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.slf4j.{Logger => Slf4jLogger}

import scala.collection.JavaConverters._
import scala.collection.mutable

object LogbackCapturingAppender {

  def apply(slf4jLogger: Slf4jLogger): LogbackCapturingAppender =
    new LogbackCapturingAppender(slf4jLogger.asInstanceOf[Logger])

}

/**
  * Appender which grants access to logged events.
  *
  * @param logger a Logback instance
  */
class LogbackCapturingAppender(logger: Logger) extends AppenderBase[ILoggingEvent] {
  private val capturedEvents: mutable.Buffer[ILoggingEvent] = mutable.Buffer.empty

  override protected def append(iLoggingEvent: ILoggingEvent): Unit =
    capturedEvents.append(iLoggingEvent)

  /**
    * Starts capturing events at the configured level.
    */
  def startCapturing(): this.type = {
    setupAppender()
    start()
    detachConfiguredAppenders()
    this
  }

  protected def setupAppender(): Unit = {
    logger.setLevel(logger.getEffectiveLevel)
    logger.addAppender(this)
  }

  protected def detachConfiguredAppenders(): Unit = {
    val rootLogger = logger.getLoggerContext.getLogger(Slf4jLogger.ROOT_LOGGER_NAME)

    rootLogger.iteratorForAppenders.asScala
      .foreach(rootLogger.detachAppender)
  }

  /**
    * @return Captured events
    */
  def loggedEvents: Iterator[ILoggingEvent] = capturedEvents.iterator

  /**
    * Detaches this appender.
    */
  def cleanup(): Boolean = logger.detachAppender(this)

}
