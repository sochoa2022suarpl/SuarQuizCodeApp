package net.iessochoa.suarpl.suarquizcodeapp.view

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import net.iessochoa.suarpl.suarquizcodeapp.R

public class RegisterFragmentDirections private constructor() {
  public companion object {
    public fun actionRegisterFragmentToLoginFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_registerFragment_to_loginFragment)
  }
}
