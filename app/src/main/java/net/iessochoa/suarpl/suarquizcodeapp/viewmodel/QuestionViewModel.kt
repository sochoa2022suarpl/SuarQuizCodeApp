package net.iessochoa.suarpl.suarquizcodeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.iessochoa.suarpl.suarquizcodeapp.model.QuestionModel
import net.iessochoa.suarpl.suarquizcodeapp.model.QuestionModelProvider


class QuestionViewModel: ViewModel() {

    private var questionModelListData = MutableLiveData<List<QuestionModel>>()

    //Obtiene una lista de objetos dependiendo de la categoría seleccionada, provisional hasta integrar Repositorio
    fun getLiveDataFromCategory(selectedCategory: String): MutableLiveData<List<QuestionModel>>{
        when(selectedCategory){
            "KOTLIN" -> questionModelListData = MutableLiveData<List<QuestionModel>>(QuestionModelProvider.questionModelProviderListKotlin)
            "JAVA" -> questionModelListData = MutableLiveData<List<QuestionModel>>(QuestionModelProvider.questionModelProviderListJava)
            "REACT" -> questionModelListData = MutableLiveData<List<QuestionModel>>(QuestionModelProvider.questionModelProviderListReact)

        }
        return questionModelListData
    }


}