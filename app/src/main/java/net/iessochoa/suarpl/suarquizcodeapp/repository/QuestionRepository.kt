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
    //función que obtiene documentos desde una colección en Firebase
    fun getFirebaseQuestions(category: String): LiveData<MutableList<QuestionModel>> {
        //variable para almacenar la lista que devolveremos
        val mutableData = MutableLiveData<MutableList<QuestionModel>>()
        //Instancia a la BD Firestore, pedimos todos los documentos que hay en una categoría dada desde el ViewModel
        FirebaseFirestore.getInstance().collection(category)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    //Almacenamos los valores en una lista temporal
                    val listData = mutableListOf<QuestionModel>()
                    //Recorremos la colección, por cada documento tomamos valores y los metemos en un objeto
                    for (document in querySnapshot) {
                        val pregunta = document.get("question").toString()
                        val opcion1 = document.get("option1").toString()
                        val opcion2 = document.get("option2").toString()
                        val opcion3 = document.get("option3").toString()
                        val opcion4 = document.get("option4").toString()
                        val respuesta = document.get("answer").toString()

                        val question =
                            QuestionModel(pregunta, opcion1, opcion2, opcion3, opcion4, respuesta)
                        //añadimos el objeto a la lista temporal
                        listData.add(question)

                    }
                    //pasamos los valores de la lista temporal a la que devolveremos
                    mutableData.value = listData
                }
            }
        //Devolvemos la lista al ViewModel
        return mutableData


    }
}