import java.io.PrintWriter
import java.text.SimpleDateFormat

object generateEvents {

  var path: String = ""

  def generateNextEvent(currentDateTime: Double, stationID: String, rainFall:Float) = {

    "{" +
    "\"Creation_Time\"" +
    ": " +
    currentDateTime +
    "," +
    "\"Station\"" +
    ": " +
    " \"" +
    stationID +
    "\"" +
    "," +
    "\"Rainfall\"" +
    ": " +
    rainFall +
    "}"
  }

  def genCurrentDateTime(currentUnixTime: Double) = {
    val nanoToMilli = 1000000.0

    val df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    df.format(currentUnixTime / nanoToMilli)
  }

  def writeFile(currentTimeInMin: Double, stationID: String, rainFall: Float) {

    // System.currentTimeMillis()
    // res8: Long = 1536335039694

    val nanoSec = 1000000000.0
    val unixStartTime = 1536335039694.0 * 1000000.0
    val currentUnixTime = unixStartTime + currentTimeInMin*60*nanoSec

    val currentDateTime = genCurrentDateTime(currentUnixTime)
    val formattedCDT = currentDateTime.replaceAll("[ :]", "-")
    val fileName = path +
    stationID +
    "_" +
    formattedCDT + ".json"

    println(fileName)

    val out = new PrintWriter(fileName)
    out.println(generateNextEvent(currentUnixTime, stationID, rainFall))
    out.close()
  }

  def origScenario() {
    val r = scala.util.Random

    val stationID1 = "Bengaluru-1"
    val stationID2 = "Bengaluru-2"
    val stationID3 = "Bengaluru-3"

    // Generate rainfall data for the first 15 mins for three stations.
    // Heavy rainfall recorded.
    var currentTimeInMin = 15
    // var currRainFall = r.nextFloat * 100
    var currRainFall = 100
    writeFile(currentTimeInMin, stationID1, currRainFall)

    // var currRainFall = r.nextFloat * 100
    currRainFall = 80
    writeFile(currentTimeInMin, stationID2, currRainFall)

    // var currRainFall = r.nextFloat * 100
    currRainFall = 120
    writeFile(currentTimeInMin, stationID3, currRainFall)

    Thread.sleep(10000)

    // Generate rainfall data for the second 15 Mins for three stations.
    // Light rainfall recorded.
    // One of the events arrives out of order.

    currentTimeInMin = 30

    // currRainFall = r.nextFloat * 10
    currRainFall = 20
    writeFile(currentTimeInMin, stationID1, currRainFall)

    // out of order event.
    // var currRainFalloo = r.nextFloat * 10
    var currRainFalloo = 10
    var currentTimeInMinoo = currentTimeInMin
    var stationIDoo = stationID2
    // writeFile(currentTimeInMin, stationID2, currRainFall)

    // currRainFall = r.nextFloat * 10
    currRainFall = 30
    writeFile(currentTimeInMin, stationID3, currRainFall)

    Thread.sleep(10000)

    // Generate rainfall data for the third 15 mins for three stations.
    // Again heavy rainfall.
    // The OO event arrives now. 

    currentTimeInMin = 45

    // currRainFall = r.nextFloat * 100
    currRainFall = 100
    writeFile(currentTimeInMin, stationID1, currRainFall)

    // currRainFall = r.nextFloat * 100
    currRainFall = 80
    writeFile(currentTimeInMin, stationID2, currRainFall)

    // currRainFall = r.nextFloat * 100
    currRainFall = 120
    writeFile(currentTimeInMin, stationID3, currRainFall)

    // out of order event.
    writeFile(currentTimeInMinoo, stationIDoo, currRainFalloo)

  }

  def threeWindows() {
    val r = scala.util.Random

    val stationID1 = "Bengaluru-1"
    val stationID2 = "Bengaluru-2"
    val stationID3 = "Bengaluru-3"

    // Generate rainfall data for the first 15 mins for three stations.
    // Heavy rainfall recorded.
    var currentTimeInMin = 15

    var currRainFall = 100
    writeFile(currentTimeInMin, stationID1, currRainFall)

    currRainFall = 80
    writeFile(currentTimeInMin, stationID2, currRainFall)

    currRainFall = 120
    writeFile(currentTimeInMin, stationID3, currRainFall)

    Thread.sleep(10000)

    // Generate rainfall data for the second 15 Mins for three stations.
    // Light rainfall recorded.

    currentTimeInMin = 30

    currRainFall = 20
    writeFile(currentTimeInMin, stationID1, currRainFall)

    currRainFall = 10
    writeFile(currentTimeInMin, stationID2, currRainFall)

    currRainFall = 30
    writeFile(currentTimeInMin, stationID3, currRainFall)

    Thread.sleep(10000)

    // Generate rainfall data for the third 15 mins for three stations.
    // Again heavy rainfall.

    currentTimeInMin = 45

    currRainFall = 100
    writeFile(currentTimeInMin, stationID1, currRainFall)

    currRainFall = 80
    writeFile(currentTimeInMin, stationID2, currRainFall)

    currRainFall = 120
    writeFile(currentTimeInMin, stationID3, currRainFall)

  }

  def main(args: Array[String]) {

    path = args(0)

    threeWindows()
  }
}

