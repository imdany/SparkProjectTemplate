package org.apache.spark.sql.utils;

import org.apache.spark.internal.Logging
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions.udf


object Transformers extends Logging {

  // UDF deinition
  def test_upper(s: String): String = {
      s.toUpperCase
  }
  val test_upperUDF = udf[String, String](test_upper)


  // UDF names - to make it easier to maintain
  private[utils] val udfNames = Map(
    test_upperUDF -> "test_upperUDF"
  )

  // Registration of all UDFs
  private[utils] def registerFunctions(sqlContext: SQLContext): Unit = {
    sqlContext.udf.register(udfNames(test_upperUDF), test_upperUDF)
  }

}
