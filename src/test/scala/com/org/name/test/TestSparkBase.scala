package com.org.name.test

import com.holdenkarau.spark.testing.DatasetSuiteBase
import com.org.name.util.LoggingUtil
import org.apache.log4j.{Appender, Level, LogManager, PatternLayout}
import org.apache.spark.Logging
import org.apache.spark.sql.utils.Main
import org.scalatest._

abstract class TestSparkBase extends FunSuite
  with DatasetSuiteBase
  with TestSparkConfig
  with Logging
  with BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    LoggingUtil.setupLogging()
    val rootLogger = LogManager.getRootLogger
    val loggersEnum = rootLogger.getAllAppenders
    while (loggersEnum.hasMoreElements) {
      val logger = loggersEnum.nextElement().asInstanceOf[Appender]
      logger.setLayout(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p %-60.60c: %m%n"))
      logDebug(s"logger: ${logger.getName}")
    }

    LogManager.getLogger("com.org.name.test").setLevel(Level.DEBUG)

    super.beforeAll()
    spark.sparkContext.setLogLevel("DEBUG")
    Main.init(spark.sqlContext)

  }

  override def afterAll(): Unit = {
    // LOGInfo("TestSparkBase afterAll().")
    super.afterAll()
  }


}
