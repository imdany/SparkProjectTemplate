package com.org.name.transformers

import com.org.name.transformers.constants.ColumnConstants._
import org.apache.spark.ml.Transformer
import org.apache.spark.ml.param.{Param, ParamMap}
import org.apache.spark.ml.util.{DefaultParamsWritable, Identifiable}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Dataset}

// Example of transformer

// Generates a new ID for each row.
// By default this new column is called id, set up by the global var ID_COLUMN
// but this can be overwritten with a parameter .setIdAttributeName("")

// Usage:
//   new IDGenerator().setIdAttributeName("ID")
//

class IDGenerator(override val uid: String)
  extends Transformer with DefaultParamsWritable {

  final val idAttributeName: Param[String] = new Param[String](this,
    "idAttributeName", "ID Attribute Name")

  def getIdAttributeName: String = $(idAttributeName)

  def setIdAttributeName(value: String): this.type = set(idAttributeName, value)

  setDefault(idAttributeName -> ID_COLUMN)

  def this() = this(Identifiable.randomUID("IDGenerator"))

  override def transform(dataset: Dataset[_]): DataFrame = {
    logInfo("Setting up IDGenerator")

    val newDataset = dataset
      // Add an ID column
      .withColumn($(idAttributeName), monotonically_increasing_id())
    newDataset
  }

  override def transformSchema(schema: StructType): StructType = {
    schema
  }

  override def copy(extra: ParamMap): IDGenerator = defaultCopy(extra)

}
