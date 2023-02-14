package net.iessochoa.suarpl.suarquizcodeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.iessochoa.suarpl.suarquizcodeapp.model.QuestionModel
import net.iessochoa.suarpl.suarquizcodeapp.model.QuestionModelProvider
import net.iessochoa.suarpl.suarquizcodeapp.repository.QuestionRepository


class QuestionViewModel: ViewModel() {

    private var questionModelListData = MutableLiveData<List<QuestionModel>>()

    //Obtiene una lista de objetos dependiendo de la categoría seleccionada, provisional hasta integrar Repositorio
    fun getLiveDataFromCategory(selectedCategory: String): LiveData<MutableList<QuestionModel>> {
        var mutableData = MutableLiveData<MutableList<QuestionModel>>()
        val repo = QuestionRepository()

        when(selectedCategory){
    "KOTLIN" ->         repo.getFirebaseQuestions("KOTLINQUESTIONS").observeForever {
        mutableData.value = it
    }
    "JAVA" ->         repo.getFirebaseQuestions("JAVAQUESTIONS").observeForever {
        mutableData.value = it
    }
    "REACT" ->         repo.getFirebaseQuestions("REACTQUESTIONS").observeForever {
        mutableData.value = it
    }

}

        return mutableData
    }


}