import org.apache.mahout.cf.taste.impl.common.FastIDSet
import org.apache.mahout.cf.taste.model.DataModel
import org.apache.mahout.cf.taste.recommender.IDRescorer

class GenderRescorer(men: FastIDSet, women: FastIDSet, userId: Long, usersRateMoreMen: FastIDSet, usersRateLessMen: FastIDSet, model: DataModel) extends IDRescorer {
  val preferenceIsMen = this.determinePreference()
  //find out which gender is the member's preference
  def getPerferenceIsMen: Boolean = {
    this.preferenceIsMen
  }
  def determinePreference(): Boolean = {
    if (usersRateMoreMen.contains(this.userId)) {
      true
    }
    if (usersRateLessMen.contains(this.userId)) {
      false
    }
    //gets the profileIds that the user has rated
    val prefs = model.getPreferencesFromUser(this.userId)
    val prefsLength = prefs.length()
    var preferesMen = 0
    var preferesWomen = 0
    for (i <- 0 to prefsLength - 1) {
      if (this.men.contains(prefs.get(i).getItemID)) {
        preferesMen = preferesMen + 1
      } else if (this.women.contains(prefs.get(i).getItemID)) {
        preferesWomen = preferesWomen + 1
      }
    }

    val ratesMoreMen: Boolean = preferesMen > preferesWomen
    if (ratesMoreMen) {
      usersRateMoreMen.add(this.userId)
    } else {
      usersRateLessMen.add(this.userId)
    }
    ratesMoreMen
  }

  override def rescore(profileId: Long, originalScore: Double): Double = {
    if (!this.isFiltered(profileId)) {
      originalScore
    } else {
      Double.NaN
    }
  }

  override def isFiltered(profileId: Long): Boolean = {
    if (this.preferenceIsMen) {
      women.contains(profileId)
    } else {
      men.contains(profileId)
    }
  }

}