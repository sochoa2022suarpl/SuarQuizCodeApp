package net.iessochoa.suarpl.suarquizcodeapp.view

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import net.iessochoa.suarpl.suarquizcodeapp.R

public class QuizFragmentDirections private constructor() {
  public companion object {
    public fun actionQuizFragmentToResultsFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_quizFragment_to_resultsFragment)

    public fun actionQuizFragmentToHomeFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_quizFragment_to_homeFragment)
  }
}
