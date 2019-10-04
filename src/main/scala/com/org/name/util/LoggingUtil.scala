package com.org.name.util

import org.apache.log4j.{Level, Logger}

object LoggingUtil {
  def setupLogging(): Unit = {
    Logger.getLogger("org").setLevel(Level.WARN)
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.apache.hadoop.util.NativeCodeLoader").setLevel(Level.ERROR)
    Logger.getLogger("privateLog").setLevel(Level.WARN)
    Logger.getLogger("akka").setLevel(Level.WARN)
    Logger.getLogger("io.netty").setLevel(Level.WARN)
  }
}
