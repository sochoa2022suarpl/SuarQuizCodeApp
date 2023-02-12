package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.String
import kotlin.jvm.JvmStatic

public data class QuizFragmentArgs(
  public val secondsLeft: Int,
  public val category: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("secondsLeft", this.secondsLeft)
    result.putString("category", this.category)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("secondsLeft", this.secondsLeft)
    result.set("category", this.category)
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
      val __category : String?
      if (bundle.containsKey("category")) {
        __category = bundle.getString("category")
        if (__category == null) {
          throw IllegalArgumentException("Argument \"category\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"category\" is missing and does not have an android:defaultValue")
      }
      return QuizFragmentArgs(__secondsLeft, __category)
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
      val __category : String?
      if (savedStateHandle.contains("category")) {
        __category = savedStateHandle["category"]
        if (__category == null) {
          throw IllegalArgumentException("Argument \"category\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"category\" is missing and does not have an android:defaultValue")
      }
      return QuizFragmentArgs(__secondsLeft, __category)
    }
  }
}
