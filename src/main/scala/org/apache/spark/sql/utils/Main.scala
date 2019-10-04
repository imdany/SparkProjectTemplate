package org.apache.spark.sql.utils
import org.apache.spark.sql.SQLContext


// Entry point for registering all UDFs
object Main {
  def init(sqlContext: SQLContext): Unit = {
    UdfRegistrator.registerAll(sqlContext)
  }
}
