package com.org.name.test

import com.holdenkarau.spark.testing.SparkContextProvider
import org.apache.spark.SparkConf

trait TestSparkConfig extends SparkContextProvider {

  override def conf: SparkConf = {
    new SparkConf()
      .setMaster("local[4]")
      .setAppName("test")
      .set("spark.ui.enabled", "false")
      .set("spark.app.id", appID)
      .set("spark.driver.host", "localhost")
      // .set("spark.executor.memory", "8g")
      .set("spark.job.description", "test")
      // .set("spark.serializer", classOf[KryoSerializer].getName)
      // .set("spark.kryo.registrator", classOf[GeoSparkKryoRegistrator].getName)
      // .set("spark.kryoserializer.gm_buffer.max", "1g")
      // .set("spark.kryo.registrator", classOf[geotrellis.spark.io.kryo.KryoRegistrator].getName)
      .set("spark.sql.parquet.binaryAsString", "true")
  }
}
