package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import kotlin.Int
import kotlin.String
import net.iessochoa.suarpl.suarquizcodeapp.R

public class HomeFragmentDirections private constructor() {
  private data class ActionHomeFragmentToQuizFragment(
    public val secondsLeft: Int,
    public val category: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_homeFragment_to_quizFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("secondsLeft", this.secondsLeft)
        result.putString("category", this.category)
        return result
      }
  }

  private data class ActionHomeFragmentToShopFragment(
    public val currentCoins: String = "0"
  ) : NavDirections {
    public override val actionId: Int = R.id.action_homeFragment_to_shopFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("currentCoins", this.currentCoins)
        return result
      }
  }

  public companion object {
    public fun actionHomeFragmentToQuizFragment(secondsLeft: Int, category: String): NavDirections =
        ActionHomeFragmentToQuizFragment(secondsLeft, category)

    public fun actionHomeFragmentToLoginFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_homeFragment_to_loginFragment)

    public fun actionHomeFragmentToScoreFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_homeFragment_to_scoreFragment)

    public fun actionHomeFragmentToShopFragment(currentCoins: String = "0"): NavDirections =
        ActionHomeFragmentToShopFragment(currentCoins)
  }
}
