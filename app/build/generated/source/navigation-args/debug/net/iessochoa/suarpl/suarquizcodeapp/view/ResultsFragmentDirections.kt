package net.iessochoa.suarpl.suarquizcodeapp.view

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import net.iessochoa.suarpl.suarquizcodeapp.R

public class ResultsFragmentDirections private constructor() {
  public companion object {
    public fun actionResultsFragmentToHomeFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_resultsFragment_to_homeFragment)
  }
}
