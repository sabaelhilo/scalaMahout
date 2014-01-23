import org.apache.mahout.cf.taste.similarity.UserSimilarity
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood
import org.apache.mahout.cf.taste.eval.RecommenderBuilder
import org.apache.mahout.cf.taste.model.DataModel
import org.apache.mahout.cf.taste.recommender.Recommender
import org.apache.mahout.cf.taste.recommender.RecommendedItem
import org.apache.mahout.cf.taste.recommender.IDRescorer
import org.apache.mahout.cf.taste.impl.common.FastIDSet
import org.apache.mahout.cf.taste.common.Refreshable

class MyRecommender (similarity: UserSimilarity, neighborhood: Int, dataModel: DataModel) extends Recommender {
  val nearestNeighborhood = new NearestNUserNeighborhood(neighborhood, similarity, dataModel)
  val userBasedRecommender = new GenericUserBasedRecommender(dataModel, nearestNeighborhood, similarity)
  val recommenderBuilder = new RecommenderBuilder() {
    override def buildRecommender(dataModel: DataModel): Recommender = {
      new GenericUserBasedRecommender(dataModel, nearestNeighborhood, similarity)
    }
  }

  def buildRescorer(userId: Long): GenderRescorer = {
    val gender = new Gender("/Users/saba/learning/mahout2-0.8/mahoutScala/src/main/resources/gender.dat")
    gender.parseGender
    val userRatesMoreMen: FastIDSet = new FastIDSet(50000)
    val userRatesLessMen: FastIDSet = new FastIDSet(50000)
    val genderRescorer = new GenderRescorer(gender.men, gender.women, userId, userRatesMoreMen, userRatesLessMen, dataModel)
    genderRescorer
  }

  override def estimatePreference(userId: Long, profileId: Long): Float = {
    val genderRescorer = buildRescorer(userId)
    genderRescorer.rescore(profileId, userBasedRecommender.estimatePreference(userId, profileId)).toFloat
  }

  override def getDataModel = {
    userBasedRecommender.getDataModel
  }

  override def recommend(userId: Long, howMany: Int, rescorer: IDRescorer): java.util.List[RecommendedItem] = {
    userBasedRecommender.recommend(userId, howMany, rescorer)
  }

  override def recommend(userId: Long, howMany: Int): java.util.List[RecommendedItem] = {
    userBasedRecommender.recommend(userId, howMany)
  }

  override def removePreference(userId: Long, itemId: Long): Unit = {
    userBasedRecommender.removePreference(userId, itemId)
  }

  override def setPreference(userId: Long, itemId: Long, value: Float): Unit = {
    userBasedRecommender.setPreference(userId, itemId, value)
  }

   override def refresh(alreadyRefreshed: java.util.Collection[Refreshable]) = {
    userBasedRecommender.refresh(alreadyRefreshed)
  }
}


