name := "SparkProjectTemplate"

version := "0.1"

scalaVersion := "2.11.12"


val SparkVersion = "2.4.4"
val HadoopVersion = "2.7.3"

val scalacheck = "org.scalacheck" %% "scalacheck" % "1.13.4"
val scalatest = "org.scalatest" %% "scalatest" % "3.0.5"

logLevel := Level.Warn

logLevel in assembly := Level.Info
test in assembly := {}

testOptions in Test += Tests.Argument("-oF")

// Don't execute in parallel since we can't have multiple Sparks in the same JVM
parallelExecution in Test := false

logBuffered in Test := false
fork in Test := true

// Configurations to speed up tests and reduce memory footprint
javaOptions in Test ++= Seq(
  "-Dspark.ui.enabled=false",
  "-Dspark.ui.showConsoleProgress=false"
)

// Only turn on coverage when we run the release process
coverageEnabled := false
coverageMinimum := 80.00
coverageFailOnMinimum := true
coverageHighlighting := false


libraryDependencies ++= Seq(

  "org.apache.spark" %% "spark-core" % SparkVersion % "provided"
    exclude("org.apache.hadoop", "*"),
  "org.apache.spark" %% "spark-sql" % SparkVersion % "provided"
    exclude("org.apache.hadoop", "*"),
  "org.apache.spark" %% "spark-mllib" % SparkVersion % "provided"
    exclude("org.apache.hadoop", "*"),

  "org.apache.hadoop" % "hadoop-mapreduce-client-core" % HadoopVersion % "provided" excludeAll(
    ExclusionRule(organization = "javax.servlet", name = "servlet-api"),
    ExclusionRule(organization = "com.sun.jersey")
  ),
  "org.apache.hadoop" % "hadoop-common" % HadoopVersion % "provided" excludeAll(
    ExclusionRule(organization = "javax.servlet", name = "servlet-api"),
    ExclusionRule(organization = "com.sun.jersey"),
    ExclusionRule(organization = "com.google.guava")
  ),

  // Logging
  "org.slf4j" % "slf4j-api" % "1.7.16" % "provided",
  "org.slf4j" % "slf4j-log4j12" % "1.7.16" % "provided",

  // Testing
  scalacheck,
  scalatest ,
  "com.holdenkarau" %% "spark-testing-base" % "2.4.0_0.11.0"
    exclude("org.apache.hadoop", "*"),
  "org.apache.spark" %% "spark-hive" % SparkVersion

)

assemblyJarName in assembly := s"${name.value}-assembly-${version.value}.jar"
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", "spark", "sql", _*) => MergeStrategy.first
  case PathList("org", "apache", "spark", "geomesa", _*) => MergeStrategy.first

  // Remove libraries that are already in Databricks
  case PathList("scala", _*) => MergeStrategy.discard
  case PathList("com", "fasterxml", _*) => MergeStrategy.discard
  case PathList("com", "google", _*) => MergeStrategy.discard
  case PathList("org", "apache", "ant", _*) => MergeStrategy.discard
  case PathList("org", "codehaus", _*) => MergeStrategy.discard
  case PathList("org", "hamcrest", _*) => MergeStrategy.discard
  case PathList("org", "junit", _*) => MergeStrategy.discard
  case PathList("org", "apache", "arrow", _*) => MergeStrategy.discard
  case PathList("org", "apache", "avro", _*) => MergeStrategy.discard
  case PathList("org", "apache", "calcite", _*) => MergeStrategy.discard
  case PathList("org", "apache", "commons", _*) => MergeStrategy.discard
  case PathList("org", "apache", "curator", _*) => MergeStrategy.discard
  case PathList("org", "apache", "derby", _*) => MergeStrategy.discard
  case PathList("org", "apache", "directory", _*) => MergeStrategy.discard
  case PathList("org", "apache", "hadoop", _*) => MergeStrategy.discard
  case PathList("org", "apache", "httpcomponents", _*) => MergeStrategy.discard
  case PathList("org", "apache", "ivy", _*) => MergeStrategy.discard
  case PathList("org", "apache", "orc", _*) => MergeStrategy.discard
  case PathList("org", "apache", "parquet", _*) => MergeStrategy.discard
  case PathList("org", "apache", "thrift", _*) => MergeStrategy.discard
  case PathList("org", "apache", "xbean", _*) => MergeStrategy.discard
  case PathList("org", "apache", "spark", _*) => MergeStrategy.discard
  case PathList("org", "slf4j", _*) => MergeStrategy.discard
  case PathList("com", "amazonaws", _*) => MergeStrategy.discard
  case PathList("com", "databricks", _*) => MergeStrategy.discard
  case PathList("org", "spire-math", _*) => MergeStrategy.discard
  case x if Assembly.isConfigFile(x) =>
    MergeStrategy.concat
  case PathList(ps @ _*) if Assembly.isReadme(ps.last) || Assembly.isLicenseFile(ps.last) =>
    MergeStrategy.rename
  case PathList("META-INF", xs @ _*) =>
    xs map {_.toLowerCase} match {
      case "manifest.mf" :: Nil | "index.list" :: Nil | "dependencies" :: Nil =>
        MergeStrategy.discard
      case ps @ _ :: _ if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
        MergeStrategy.discard
      case "plexus" :: _ =>
        MergeStrategy.discard
      case "services" :: _ =>
        MergeStrategy.filterDistinctLines
      case "spring.schemas" :: Nil | "spring.handlers" :: Nil =>
        MergeStrategy.filterDistinctLines
      case _ => MergeStrategy.first
    }
  case _ => MergeStrategy.first
  //  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  //  case path if path.endsWith(".SF") => MergeStrategy.discard
  //  case path if path.endsWith(".DSA") => MergeStrategy.discard
  //  case path if path.endsWith(".RSA") => MergeStrategy.discard
  //  case _ => MergeStrategy.first
}

resolvers += "Spark Packages" at "https://dl.bintray.com/spark-packages/maven/"