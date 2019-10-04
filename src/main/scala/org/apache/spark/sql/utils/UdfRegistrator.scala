package org.apache.spark.sql.utils;

import org.apache.spark.sql.types.UDTRegistration
import org.apache.spark.sql.{SQLContext, SparkSession}

object UdfRegistrator {

  def registerAll(sqlContext: SQLContext): Unit = {
    Transformers.registerFunctions(sqlContext)
  }

  def exists(userClassName: String): Boolean = UDTRegistration.exists(userClassName)

}
