package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class QuizFragmentArgs(
  public val secondsLeft: Int
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("secondsLeft", this.secondsLeft)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("secondsLeft", this.secondsLeft)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): QuizFragmentArgs {
      bundle.setClassLoader(QuizFragmentArgs::class.java.classLoader)
      val __secondsLeft : Int
      if (bundle.containsKey("secondsLeft")) {
        __secondsLeft = bundle.getInt("secondsLeft")
      } else {
        throw IllegalArgumentException("Required argument \"secondsLeft\" is missing and does not have an android:defaultValue")
      }
      return QuizFragmentArgs(__secondsLeft)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): QuizFragmentArgs {
      val __secondsLeft : Int?
      if (savedStateHandle.contains("secondsLeft")) {
        __secondsLeft = savedStateHandle["secondsLeft"]
        if (__secondsLeft == null) {
          throw IllegalArgumentException("Argument \"secondsLeft\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"secondsLeft\" is missing and does not have an android:defaultValue")
      }
      return QuizFragmentArgs(__secondsLeft)
    }
  }
}
