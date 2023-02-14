package net.iessochoa.suarpl.suarquizcodeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.iessochoa.suarpl.suarquizcodeapp.model.QuestionModel
import net.iessochoa.suarpl.suarquizcodeapp.model.QuestionModelProvider
import net.iessochoa.suarpl.suarquizcodeapp.repository.QuestionRepository

//Obtiene una lista de objetos dependiendo de la categoría seleccionada
class QuestionViewModel: ViewModel() {

    private var questionModelListData = MutableLiveData<List<QuestionModel>>()


    fun getLiveDataFromCategory(selectedCategory: String): LiveData<MutableList<QuestionModel>> {
        // variable para almacenar el livedata de tipo Mutablelist que obtendremos desde el repositorio
        val mutableData = MutableLiveData<MutableList<QuestionModel>>()
        //Instancia de repositorio
        val repo = QuestionRepository()
        //dependiendo de la categoría seleccionada en el RecyclerView, pide al repositorio que obtenga los datos, colocando un observer
        repo.getFirebaseQuestions(selectedCategory).observeForever {
            mutableData.value = it
        /*
        //old method, cambiar las BD en Firestore para adaptar el nuevo
        when(selectedCategory){
            "KOTLIN" ->         repo.getFirebaseQuestions("KOTLINQUESTIONS").observeForever {
        mutableData.value = it
            }
            "REACT" ->         repo.getFirebaseQuestions("REACTQUESTIONS").observeForever {
        mutableData.value = it
            }
            "HTML" ->         repo.getFirebaseQuestions("HTMLQUESTIONS").observeForever {
        mutableData.value = it
            }
            "NO" ->         repo.getFirebaseQuestions("HTMLQUESTIONS").observeForever {
                mutableData.value = it
            }

         */

}
        //devolvemos el fetch obtenido desde el repositorio a la vista
        return mutableData
    }


}