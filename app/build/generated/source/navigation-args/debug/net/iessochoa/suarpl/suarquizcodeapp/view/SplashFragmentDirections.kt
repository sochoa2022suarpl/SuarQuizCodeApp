package net.iessochoa.suarpl.suarquizcodeapp.view

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import net.iessochoa.suarpl.suarquizcodeapp.R

public class SplashFragmentDirections private constructor() {
  public companion object {
    public fun actionSplashFragmentToLoginFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_splashFragment_to_loginFragment)
  }
}
