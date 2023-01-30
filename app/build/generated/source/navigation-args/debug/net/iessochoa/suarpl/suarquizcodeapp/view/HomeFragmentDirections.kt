package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import kotlin.Int
import net.iessochoa.suarpl.suarquizcodeapp.R

public class HomeFragmentDirections private constructor() {
  private data class ActionHomeFragmentToQuizFragment(
    public val secondsLeft: Int
  ) : NavDirections {
    public override val actionId: Int = R.id.action_homeFragment_to_quizFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("secondsLeft", this.secondsLeft)
        return result
      }
  }

  public companion object {
    public fun actionHomeFragmentToQuizFragment(secondsLeft: Int): NavDirections =
        ActionHomeFragmentToQuizFragment(secondsLeft)

    public fun actionHomeFragmentToLoginFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_homeFragment_to_loginFragment)
  }
}
