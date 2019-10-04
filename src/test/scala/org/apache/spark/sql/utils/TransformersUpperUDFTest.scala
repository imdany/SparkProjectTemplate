package org.apache.spark.sql.utils

import com.org.name.test.TestSparkBase
import org.apache.spark.sql.utils.Transformers.test_upper

class TransformersUpperUDFTest extends TestSparkBase {

  test ("Testing upper functionality"){
    logDebug("Starting upper test:")

    val result = test_upper("Hello")
    assert(result.equals("HELLO"), "Function works")
    logDebug("[+] Test 0: Function works")
  }

  test("Testing Upper functionality as UDF") {
    import spark.implicits._

    logDebug("Starting upper UDF test:")

    val functionRegistered = spark.catalog.listFunctions
        .filter("name like '%test_upperUDF%'")
        .select("name").first().getString(0)

    assert(!functionRegistered.isEmpty, "Function is registered")
    logDebug("[+] Test 1: Function is registered")

    val inputDF = Seq(
      ("A", 1, "row1"),
      ("B", 2, "row2"),
      ("C", 3, "row3")
    ).toDF("Col1", "Col2", "Col3")

    val result = inputDF.selectExpr("*", "test_upperUDF(Col3) as ColUpper")

    val row1 = result.select("ColUpper").first().getString(0)

    assert(row1.equals("ROW1"), "UDF works")
    logInfo("[+] Test 2: UDF works")
  }

}
