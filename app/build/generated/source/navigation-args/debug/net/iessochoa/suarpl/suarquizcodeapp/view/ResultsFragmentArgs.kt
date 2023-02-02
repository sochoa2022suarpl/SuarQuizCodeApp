package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class ResultsFragmentArgs(
  public val secondsBonus: Int,
  public val resRight: Int,
  public val resWrong: Int
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("secondsBonus", this.secondsBonus)
    result.putInt("resRight", this.resRight)
    result.putInt("resWrong", this.resWrong)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("secondsBonus", this.secondsBonus)
    result.set("resRight", this.resRight)
    result.set("resWrong", this.resWrong)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): ResultsFragmentArgs {
      bundle.setClassLoader(ResultsFragmentArgs::class.java.classLoader)
      val __secondsBonus : Int
      if (bundle.containsKey("secondsBonus")) {
        __secondsBonus = bundle.getInt("secondsBonus")
      } else {
        throw IllegalArgumentException("Required argument \"secondsBonus\" is missing and does not have an android:defaultValue")
      }
      val __resRight : Int
      if (bundle.containsKey("resRight")) {
        __resRight = bundle.getInt("resRight")
      } else {
        throw IllegalArgumentException("Required argument \"resRight\" is missing and does not have an android:defaultValue")
      }
      val __resWrong : Int
      if (bundle.containsKey("resWrong")) {
        __resWrong = bundle.getInt("resWrong")
      } else {
        throw IllegalArgumentException("Required argument \"resWrong\" is missing and does not have an android:defaultValue")
      }
      return ResultsFragmentArgs(__secondsBonus, __resRight, __resWrong)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): ResultsFragmentArgs {
      val __secondsBonus : Int?
      if (savedStateHandle.contains("secondsBonus")) {
        __secondsBonus = savedStateHandle["secondsBonus"]
        if (__secondsBonus == null) {
          throw IllegalArgumentException("Argument \"secondsBonus\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"secondsBonus\" is missing and does not have an android:defaultValue")
      }
      val __resRight : Int?
      if (savedStateHandle.contains("resRight")) {
        __resRight = savedStateHandle["resRight"]
        if (__resRight == null) {
          throw IllegalArgumentException("Argument \"resRight\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"resRight\" is missing and does not have an android:defaultValue")
      }
      val __resWrong : Int?
      if (savedStateHandle.contains("resWrong")) {
        __resWrong = savedStateHandle["resWrong"]
        if (__resWrong == null) {
          throw IllegalArgumentException("Argument \"resWrong\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"resWrong\" is missing and does not have an android:defaultValue")
      }
      return ResultsFragmentArgs(__secondsBonus, __resRight, __resWrong)
    }
  }
}
