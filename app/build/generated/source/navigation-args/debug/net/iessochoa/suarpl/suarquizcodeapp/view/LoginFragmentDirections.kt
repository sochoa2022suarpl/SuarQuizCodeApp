package net.iessochoa.suarpl.suarquizcodeapp.view

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import net.iessochoa.suarpl.suarquizcodeapp.R

public class LoginFragmentDirections private constructor() {
  public companion object {
    public fun actionLoginFragmentToRegisterFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_loginFragment_to_registerFragment)

    public fun actionLoginFragmentToHomeFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_loginFragment_to_homeFragment)
  }
}
