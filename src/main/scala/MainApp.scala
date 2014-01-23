import org.apache.mahout.cf.taste.impl.model.file.FileDataModel
import java.io.File
import org.apache.mahout.cf.taste.impl.common.FastIDSet
import org.apache.mahout.cf.taste.recommender.IDRescorer

object MainApp {
  def main(args : Array[String]) {
    val path = "/Users/saba/learning/mahout2-0.8/mahoutScala/src/main/resources/ratings.dat"
    val model = new FileDataModel(new File(path))
    val userId = 5
    val neighborhood = 2
    val userSimilarity = new Similarity("Euclidean", model)
    val recommender = new MyRecommender(userSimilarity.similarityMetric, neighborhood, model)
    val delegate = recommender.userBasedRecommender
    val rescorer = recommender.buildRescorer(userId)
    println(delegate.recommend(userId, 2, rescorer))
  }
}
