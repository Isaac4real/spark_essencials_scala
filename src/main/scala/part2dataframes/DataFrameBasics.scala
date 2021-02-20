package part2dataframes

import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SparkSession}

object DataFrameBasics extends App {
  // creating a SparkSession
  val spark = SparkSession.builder()
    .appName("Dataframes Basics")
    .config("spark.master", "local")
    .getOrCreate()

  // reading a DF
  val firstDF = spark.read
    .format("json")
    .option("inferSchema", "true")
    .load("src/main/resources/data/cars.json")

  // showing df
  firstDF.show()
  firstDF.printSchema()

  // get rows
  firstDF.take(10).foreach(println)

  // spark types
  val longType = LongType

  // schema
  val carsSchema = StructType(Array(
    StructField("Name", StringType),
    StructField("Miles_per_Galon", DoubleType),
    StructField("Cylinders", LongType),
    StructField("Displacement", DoubleType),
    StructField("Horsepower", LongType),
    StructField("Weight_in_lbs", LongType),
    StructField("Acceleration", DoubleType),
    StructField("Year", StringType),
    StructField("Origin", StringType)
  ))

  // obtain schema
  val carsFDSchema = firstDF.schema

  // read a DF with ur schema
  val carsWithSchema = spark.read
    .format("json")
    .schema(carsFDSchema)
    .load("src/main/resources/data/cars.json")


  // created rows by hand
  var myRow = Row("Ford", 18, 8, 307, 130, 3504, 12.0, "1970-01-01", "USA")

  // create DF from tuples
  var cars = Seq(
    ("chevrolet chevelle malibu", 18, 8, 307, 130, 3504, 12.0, "1970-01-01", "USA"),
    ("buick skylark 320", 15, 8, 350, 165, 3693, 11.5, "1970-01-01", "USA"),
    ("plymouth satellite", 18, 8, 318, 150, 3436, 11.0, "1970-01-01", "USA"),
    ("amc rebel sst", 16, 8, 304, 150, 3433, 12.0, "1970-01-01", "USA"),
    ("ford torino", 17, 8, 302, 140, 3449, 10.5, "1970-01-01", "USA"),
    ("ford galaxie 500", 15, 8, 429, 198, 4341, 10.0, "1970-01-01", "USA"),
    ("chevrolet impala", 14, 8, 454, 220, 4354, 9.0, "1970-01-01", "USA"),
    ("plymouth fury iii", 14, 8, 440, 215, 4312, 8.5, "1970-01-01", "USA"),
    ("pontiac catalina", 14, 8, 455, 225, 4425, 10.0, "1970-01-01", "USA"),
    ("amc ambassador dpl", 15, 8, 390, 190, 3850, 8.5, "1970-01-01", "USA")
  )

  val manualCarsDF = spark.createDataFrame(cars)
  // note: DFs have schemas, rows do not

  // create DFs with implicits

  import spark.implicits._

  val manualCarsDFWithImplicits = cars.toDF("Name", "MPG", "Cylinders", "Displacement", "HP", "Weight", "Acceleration", "Year", "CountryOrigin")

  /**
    * Exercise:
    * 1) Create a manual DF describing smartphones
    *   - make
    *   - model
    *   - screen dimension
    *   - camera megapixels
    *
    * 2) Read another file from the data/ folder, e.g. movies.json
    *   - print its schema
    *   - count the number of rows, call count()
    */

  val cols = Array("make", "model", "screen dim", "camera")
  val smartphoneSchema = StructType(cols.map(col => StructField(col, StringType)))
  val smartphonesData = spark.sparkContext.parallelize(Seq(
    Row("...", "...", "...", "..."),
    Row("...", "...", "...", "..."))
  )
  val smartphoneDF = spark.createDataFrame(smartphonesData, smartphoneSchema)

  // or simply
  val smartphonedata2 = Seq(
    ("...", "...", "...", "..."),
    ("...", "...", "...", "...")
  )
  val smartphoneDF2 = smartphonedata2.toDF("make", "model", "screen dim", "camera")

  // ex 2
  val moviesDF = spark.read
    .option("inferSchema", "true")
    .format("json")
    .load("src/main/resources/data/movies.json")

  moviesDF.printSchema()
  println(s"The Movies DF has ${moviesDF.count()} rows")

}
