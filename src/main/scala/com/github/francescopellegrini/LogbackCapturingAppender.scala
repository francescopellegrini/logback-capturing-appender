package com.github.francescopellegrini

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.slf4j.{Logger => Slf4jLogger}

object LogbackCapturingAppender {
  private var allAppenders = Seq[LogbackCapturingAppender]()

  def apply(slf4jLogger: Slf4jLogger): LogbackCapturingAppender = {
    val appender = new LogbackCapturingAppender(slf4jLogger.asInstanceOf[Logger])
    allAppenders :+= appender
    appender
  }

  /**
    * Detaches all appenders.
    */
  def cleanUp(): Unit = allAppenders.foreach(_.cleanup())
}

/**
  * Appender which grants access to latest logging event.
  *
  * @param logger a Logback instance
  */
class LogbackCapturingAppender(logger: Logger) extends AppenderBase[ILoggingEvent] {
  private var capturedEvent: Option[ILoggingEvent] = None

  override protected def append(iLoggingEvent: ILoggingEvent): Unit = {
    capturedEvent = Option(iLoggingEvent)
  }

  protected def connect(): Unit = {
    logger.setLevel(logger.getEffectiveLevel)
    logger.addAppender(this)
    start()
  }

  protected def detachConfiguredAppenders(): Unit = {
    import scala.collection.JavaConverters._

    val rootLogger = logger.getLoggerContext.getLogger(Slf4jLogger.ROOT_LOGGER_NAME)

    rootLogger.iteratorForAppenders.asScala
      .foreach(rootLogger.detachAppender)
  }

  /**
    * @return Latest captured event
    */
  def loggedEvent: Option[ILoggingEvent] = capturedEvent

  /**
    * Detaches this appender.
    */
  def cleanup(): Unit = logger.detachAppender(this)

  connect()
  detachConfiguredAppenders()
}
