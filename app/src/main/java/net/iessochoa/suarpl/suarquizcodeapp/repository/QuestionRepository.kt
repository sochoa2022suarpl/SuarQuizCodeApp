package net.iessochoa.suarpl.suarquizcodeapp.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import net.iessochoa.suarpl.suarquizcodeapp.model.QuestionModel

class QuestionRepository {
    private var questionModelRepositoryListKotlin = ArrayList<QuestionModel>()


    fun getFirebaseQuestions(category: String): LiveData<MutableList<QuestionModel>> {
        val mutableData = MutableLiveData<MutableList<QuestionModel>>()
        FirebaseFirestore.getInstance().collection(category)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val listData = mutableListOf<QuestionModel>()
                    for (document in querySnapshot) {
                        val pregunta = document.get("question").toString()
                        val opcion1 = document.get("option1").toString()
                        val opcion2 = document.get("option2").toString()
                        val opcion3 = document.get("option3").toString()
                        val opcion4 = document.get("option4").toString()
                        val respuesta = document.get("answer").toString()

                        val question =
                            QuestionModel(pregunta, opcion1, opcion2, opcion3, opcion4, respuesta)
                        listData.add(question)

                    }
                    mutableData.value = listData
                }
            }
        return mutableData


    }
}