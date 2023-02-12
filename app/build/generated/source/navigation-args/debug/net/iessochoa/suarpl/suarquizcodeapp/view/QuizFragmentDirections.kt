package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import kotlin.Int
import net.iessochoa.suarpl.suarquizcodeapp.R

public class QuizFragmentDirections private constructor() {
  private data class ActionQuizFragmentToResultsFragment(
    public val secondsBonus: Int,
    public val resRight: Int,
    public val resWrong: Int
  ) : NavDirections {
    public override val actionId: Int = R.id.action_quizFragment_to_resultsFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("secondsBonus", this.secondsBonus)
        result.putInt("resRight", this.resRight)
        result.putInt("resWrong", this.resWrong)
        return result
      }
  }

  public companion object {
    public fun actionQuizFragmentToResultsFragment(
      secondsBonus: Int,
      resRight: Int,
      resWrong: Int
    ): NavDirections = ActionQuizFragmentToResultsFragment(secondsBonus, resRight, resWrong)

    public fun actionQuizFragmentToHomeFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_quizFragment_to_homeFragment)
  }
}
