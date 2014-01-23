
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity
import org.apache.mahout.cf.taste.similarity.UserSimilarity

class Similarity(similarityType: String, dataModel: FileDataModel) {
  def similarityMetric: UserSimilarity = {
    similarityType match {
      case "Euclidean" => new EuclideanDistanceSimilarity(dataModel)
      case "Pearson" => new PearsonCorrelationSimilarity(dataModel)
      case "Log-likelihood" => new LogLikelihoodSimilarity(dataModel)
      case "Tanimoto" => new TanimotoCoefficientSimilarity(dataModel)
      case _ => new EuclideanDistanceSimilarity(dataModel)
    }
  }
}