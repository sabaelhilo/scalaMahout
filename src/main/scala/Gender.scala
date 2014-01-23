import scala.io.Source
import org.apache.mahout.cf.taste.impl.common.FastIDSet

class Gender (dataFile: String) {
  val men: FastIDSet = new FastIDSet(50000)
  val women: FastIDSet = new FastIDSet(50000)

  def parseGender() {
    //read each line from the file and add to men if gender is M etc.
    for (line <- Source.fromFile(this.dataFile).getLines()) {
      val splitResult = line.split(",")
      if (splitResult(1).compareTo("F") == 0) {
        this.women.add(splitResult(0).toLong)
      } else if (splitResult(1).compareTo("M") == 0) {
        this.men.add(splitResult(0).toLong)
      }
    }
  }
}