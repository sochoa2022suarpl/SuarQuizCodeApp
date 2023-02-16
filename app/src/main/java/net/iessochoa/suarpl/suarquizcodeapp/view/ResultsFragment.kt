package net.iessochoa.suarpl.suarquizcodeapp.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentResultsBinding


class ResultsFragment : Fragment() {
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!
    //Variables para recuperar resultados acertadas/fallidas
    private val arg: ResultsFragmentArgs by navArgs()
    private lateinit var rightAnswers : String
    private lateinit var wrongAnswers : String
    private lateinit var secsBonus : String

    //Instancia Firebase
    val db = Firebase.firestore
    //Variables para calcular puntuacion
    var totalScore:Int = 0
    var currentCoins:Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btVolverHome2.setOnClickListener {
            findNavController().navigate(R.id.action_resultsFragment_to_homeFragment)
        }
        //Recuperando valores de acertadas/fallidas desde QuizFragment
        secsBonus = arg.secondsBonus.toString()
        rightAnswers = arg.resRight.toString()
        wrongAnswers = arg.resWrong.toString()



        //Muestra resultados y actualiza las monedas en la BD
        showResults()
        getUserCoins()
        
    }
    //Calcula la puntuación obtenida
    private fun showResults(){
        totalScore = rightAnswers.toInt()*100

        binding.tvAcertadas.text = rightAnswers
        binding.tvFallidas.text = wrongAnswers
        //Penalización por 5 o más preguntas fallidas
        if (wrongAnswers.toInt()>=5){
            if (rightAnswers.toInt()>0){
                totalScore -= 100
            }
        }
        binding.tvCantidadMonedas.text = totalScore.toString()
    }

    //Método que obtiene las monedas actuales del usuario logueado en Firebase Auth
    //Y le suma las ganadas, usando coroutines
    private fun getUserCoins() {
        val currentUser = FirebaseAuth.getInstance().currentUser?.email.toString()
        val docRef = db.collection("users").document(currentUser)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val document = docRef.get().await()
                if (document.get("coins") != null) {
                    currentCoins = document.get("coins").toString().toInt()
                    println("Actual" + currentCoins)
                    totalScore += currentCoins
                    println("Total" + totalScore)
                    updateUserCoins()
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "get failed with ", e)
            }
        }
    }
    //Actualizando monedas, sumando resultado obtenido usando coroutines
    private fun updateUserCoins() {
        val currentUser = FirebaseAuth.getInstance().currentUser?.email.toString()
        val docRef = db.collection("users").document(currentUser)

        CoroutineScope(Dispatchers.IO).launch {
            try {
            docRef.update("coins", totalScore.toString()).await()
            } catch (e: Exception) {
            Log.d(ContentValues.TAG, "update failed with ", e)
        }
    }
}
}