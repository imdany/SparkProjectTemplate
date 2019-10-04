package com.org.name.transformers

import org.apache.spark.ml.Pipeline
import com.org.name.test.TestSparkBase

class IDGeneratorTest extends TestSparkBase {

  test("testIDGenerator") {

    logInfo("Starting testIDGenerator...")

    import spark.implicits._

    // Input data
    val inputDF = Seq(
      ("A", 1, "row1"),
      ("B", 2, "row2"),
      ("C", 3, "row3")
    ).toDF("Col1", "Col2", "Col3")

    logDebug(f"Input Schema: ${inputDF.schema.simpleString}")

    inputDF.show(10, truncate = false)

    // Pipeline definition
    val resultDF = new Pipeline()
      .setStages(
        Array(
          new IDGenerator().setIdAttributeName("ID")
        )
      )
      .fit(inputDF)
      .transform(inputDF)

    logDebug(f"Output Schema: ${resultDF.schema.simpleString}")

    resultDF.show(10, truncate = false)

    // Check data
    assert(resultDF.count() === 3, "Must be 3 rows")

    val value = resultDF.first().getAs[Long]("ID")
    assert(value === 0, "Check value")
  }

}
